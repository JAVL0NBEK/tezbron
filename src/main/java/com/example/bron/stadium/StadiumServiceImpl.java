package com.example.bron.stadium;

import com.example.bron.auth.security.CurrentUserService;
import com.example.bron.auth.user.UserEntity;
import com.example.bron.booking.BookingEntity;
import com.example.bron.booking.BookingRepository;
import com.example.bron.enums.SlotStatus;
import com.example.bron.enums.StadiumDuration;
import com.example.bron.exception.ForbiddenException;
import com.example.bron.exception.NotFoundException;
import com.example.bron.location.DistrictRepository;
import com.example.bron.location.RegionRepository;
import com.example.bron.stadium.dto.AvailabilitySlotRequestDto;
import com.example.bron.stadium.dto.StadiumRequestDto;
import com.example.bron.stadium.dto.StadiumResponseDto;
import com.example.bron.auth.user.UserRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StadiumServiceImpl implements StadiumService {
    private final StadiumRepository stadiumRepository;
    private final BookingRepository bookingRepository;
    private final  StadiumMapper mapper;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final CurrentUserService currentUserService;

    @Override
    public StadiumResponseDto create(StadiumRequestDto dto) {
        UserEntity owner;
        Long targetDistrictId;

        if (currentUserService.isOwner()
            && !currentUserService.isSuperAdmin()
            && !currentUserService.isDistrictAdmin()) {
          owner = currentUserService.getCurrentUser();
          targetDistrictId = owner.getDistrict() == null ? null : owner.getDistrict().getId();
          if (targetDistrictId == null) {
            throw new ForbiddenException("OWNER_HAS_NO_DISTRICT");
          }
        } else {
          currentUserService.requireSameDistrict(dto.getDistrictId());
          if (dto.getOwnerId() != null) {
            owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new NotFoundException("owner_not_found",
                    List.of(dto.getOwnerId().toString())));
          } else {
            owner = null;
          }
          targetDistrictId = dto.getDistrictId();
        }

        var region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() -> new NotFoundException("region_not_found",List.of(dto.getRegionId().toString())));
      var district = districtRepository.findById(targetDistrictId)
          .orElseThrow(() -> new NotFoundException("district_not_found",List.of(targetDistrictId.toString())));

        var stadium = mapper.toEntity(dto);
        stadium.setOwner(owner);
        stadium.setRegion(region);
        stadium.setDistrict(district);
        var saved =  stadiumRepository.save(stadium);
        var response = mapper.toDto(saved);
        if (owner != null) {
          response.setOwnerName(owner.getUsername());
        }
        return response;
    }

    @Override
    public StadiumResponseDto update(Long id, StadiumRequestDto dto) {
        var entity = getFindById(id);
        currentUserService.requireOwnerOrDistrictScope(
            entity.getOwner() == null ? null : entity.getOwner().getId(),
            entity.getDistrict() == null ? null : entity.getDistrict().getId());
        if (dto.getDistrictId() != null && !currentUserService.isOwner()) {
          currentUserService.requireSameDistrict(dto.getDistrictId());
        }
        UserEntity owner = null;
        if (dto.getOwnerId() != null) {
          owner = userRepository.findById(dto.getOwnerId())
            .orElseThrow(() -> new NotFoundException("owner_not_found",List.of(dto.getOwnerId().toString())));
        }
        mapper.updateEntity(entity, dto);
        if (owner != null) {
          entity.setOwner(owner);
        }

        StadiumEntity updated = stadiumRepository.save(entity);
        var response = mapper.toDto(updated);
        UserEntity effectiveOwner = updated.getOwner();
        if (effectiveOwner != null) {
          response.setOwnerName(effectiveOwner.getUsername());
        }
        return response;
    }

    private StadiumEntity getFindById(Long id) {
        return stadiumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("stadium_not_found",List.of(id.toString())));
    }

    @Override
    public void delete(Long id) {
        var entity = getFindById(id);
        currentUserService.requireOwnerOrDistrictScope(
            entity.getOwner() == null ? null : entity.getOwner().getId(),
            entity.getDistrict() == null ? null : entity.getDistrict().getId());
        stadiumRepository.deleteById(id);
    }

    @Override
    public List<StadiumResponseDto> getById(Long id, LocalDate date, StadiumDuration duration) {
      // 1) Asl stadionni topamiz (unda ownerId bor)
      var stadium = getFindById(id);

      // Ownersiz stadion: slotlar generatsiya qilinmaydi, faqat ma'lumotlari qaytariladi
      if (stadium.getOwner() == null) {
        return List.of(mapper.toDto(stadium));
      }

      // 2) OwnerId bo‘yicha barcha stadionlarni olish
      var filter = new StadiumFilterParams();
      filter.setOwnerId(stadium.getOwner().getId());
      List<StadiumResponseDto> stadiums = stadiumRepository.getByOwnerId(filter);

      if (stadiums.isEmpty()) {
        return List.of();
      }

      // 3) Bitta query bilan barcha stadionlar uchun shu kunga tegishli bookinglarni olamiz
      List<Long> stadiumIds = stadiums.stream()
          .map(StadiumResponseDto::getId)
          .toList();

      // Per-stadium open/close vaqtlarining birlashmasi: eng erta open va eng kech close
      LocalTime earliestOpen = stadiums.stream()
          .map(s -> s.getOpenTime().toLocalTime())
          .min(LocalTime::compareTo)
          .orElse(LocalTime.MIN);
      LocalTime latestClose = stadiums.stream()
          .map(s -> s.getCloseTime().toLocalTime())
          .max(LocalTime::compareTo)
          .orElse(LocalTime.MAX);

      LocalDateTime dayRangeStart = LocalDateTime.of(date, earliestOpen);
      LocalDateTime dayRangeEnd = LocalDateTime.of(date, latestClose);

      Map<Long, List<BookingEntity>> bookingsByStadium = bookingRepository
          .findConflictingBookingsForStadiums(stadiumIds, dayRangeStart, dayRangeEnd)
          .stream()
          .collect(Collectors.groupingBy(b -> b.getStadium().getId()));

      // 4) Har bir stadion uchun slotlar generatsiya qilamiz (qo‘shimcha query yo‘q)
      return stadiums.stream()
          .map(st -> buildStadiumResponseWithSlots(
              st,
              date,
              duration,
              bookingsByStadium.getOrDefault(st.getId(), Collections.emptyList())
          ))
          .toList();
    }

    @Override
    public Page<StadiumResponseDto> getAll(StadiumFilterParams filterParams, Pageable pageable) {
      return stadiumRepository.getAllStadions(filterParams,
          pageable);
    }

  @Override
  public StadiumResponseDto updateFavorite(Long id, Boolean isFavorite) {
      var stadion = getFindById(id);
      stadion.setIsFavorite(isFavorite);
    return mapper.toDto(stadiumRepository.save(stadion));
  }

  @Transactional
  @Override
  public void updateOpenCloseTime(Long id, LocalDateTime openTime, LocalDateTime closeTime) {
    var stadion = getFindById(id);
    stadion.setOpenTime(openTime);
    stadion.setCloseTime(closeTime);
  }

  @Transactional
  public void updateAllOpenCloseTime(LocalDateTime openTime,
      LocalDateTime closeTime) {

    stadiumRepository.updateAllOpenCloseTime(openTime, closeTime);
  }

  private StadiumResponseDto buildStadiumResponseWithSlots(
      StadiumResponseDto stadium,
      LocalDate date,
      StadiumDuration duration,
      List<BookingEntity> bookings
  ) {
    LocalDateTime dayStart = LocalDateTime.of(date, stadium.getOpenTime().toLocalTime());
    LocalDateTime dayEnd = LocalDateTime.of(date, stadium.getCloseTime().toLocalTime());

    List<AvailabilitySlotRequestDto> slots = generateSlots(
        dayStart,
        dayEnd,
        duration,
        bookings
    );

    stadium.setSlots(slots);
    // shu yerda eng yaqin bo'sh vaqtni topasan
    LocalDateTime earliestAvailable = findEarliestAvailable(slots);
    stadium.setEarliestAvailable(earliestAvailable);
    return stadium;
  }

  private static final int SLOT_STEP_MINUTES = 30;

  private List<AvailabilitySlotRequestDto> generateSlots(
      LocalDateTime openDateTime,
      LocalDateTime closeDateTime,
      StadiumDuration duration,
      List<BookingEntity> bookings
  ) {
    List<AvailabilitySlotRequestDto> result = new ArrayList<>();

    LocalDateTime now = LocalDateTime.now();
    int durationMinutes = duration.getMinutes();

    LocalDateTime current = openDateTime;

    // Agar bugungi kun bo‘lsa — hozirgi vaqtni keyingi slotga suramiz
    if (openDateTime.toLocalDate().equals(now.toLocalDate()) && now.isAfter(openDateTime)) {
      current = roundUpToNextSlot(now);

      if (current.isBefore(openDateTime)) {
        current = openDateTime;
      }
    }

    while (!current.plusMinutes(durationMinutes).isAfter(closeDateTime)) {

      LocalDateTime slotStart = current;
      LocalDateTime slotEnd = slotStart.plusMinutes(durationMinutes);

      SlotStatus status = resolveSlotStatus(slotStart, slotEnd, now, bookings);

      result.add(new AvailabilitySlotRequestDto(slotStart, slotEnd, status));

      current = current.plusMinutes(SLOT_STEP_MINUTES);
    }

    return result;
  }

  private SlotStatus resolveSlotStatus(
      LocalDateTime slotStart,
      LocalDateTime slotEnd,
      LocalDateTime now,
      List<BookingEntity> bookings
  ) {
    // O‘tib ketgan vaqt
    if (!slotStart.isAfter(now)) {
      return SlotStatus.PAST;
    }

    // Booking bilan overlap
    boolean overlapsBooking = bookings.stream().anyMatch(booking ->
        slotStart.isBefore(booking.getEndTime()) &&
            slotEnd.isAfter(booking.getStartTime())
    );

    if (overlapsBooking) {
      return SlotStatus.BOOKED;
    }

    return SlotStatus.AVAILABLE;
  }

  private LocalDateTime roundUpToNextSlot(LocalDateTime dateTime) {
    int minute = dateTime.getMinute();
    int remainder = minute % 30;

    if (remainder == 0 && dateTime.getSecond() == 0) {
      return dateTime.withSecond(0).withNano(0);
    }

    int minutesToAdd = 30 - remainder;

    return dateTime.plusMinutes(minutesToAdd)
        .withSecond(0)
        .withNano(0);
  }

  private LocalDateTime findEarliestAvailable(List<AvailabilitySlotRequestDto> slots) {
    return slots.stream()
        .filter(slot -> slot.getStatus() == SlotStatus.AVAILABLE)
        .map(AvailabilitySlotRequestDto::getStart)
        .findFirst()
        .orElse(null);
  }

}
