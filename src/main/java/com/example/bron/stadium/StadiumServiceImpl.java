package com.example.bron.stadium;

import com.example.bron.booking.BookingEntity;
import com.example.bron.booking.BookingRepository;
import com.example.bron.enums.StadiumDuration;
import com.example.bron.exception.NotFoundException;
import com.example.bron.location.DistrictRepository;
import com.example.bron.location.RegionRepository;
import com.example.bron.stadium.dto.StadiumRequestDto;
import com.example.bron.stadium.dto.StadiumResponseDto;
import com.example.bron.auth.user.UserRepository;
import com.example.bron.stadium.dto.TimeRange;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StadiumServiceImpl implements StadiumService {
    private final StadiumRepository stadiumRepository;
    private final BookingRepository bookingRepository;
    private final  StadiumMapper mapper;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;

    @Override
    public StadiumResponseDto create(StadiumRequestDto dto) {
        var owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new NotFoundException("owner_not_found",List.of(dto.getOwnerId().toString())));

        var region = regionRepository.findById(dto.getRegionId())
                .orElseThrow(() -> new NotFoundException("region_not_found",List.of(dto.getRegionId().toString())));
      var district = districtRepository.findById(dto.getDistrictId())
          .orElseThrow(() -> new NotFoundException("district_not_found",List.of(dto.getDistrictId().toString())));

        var stadium = mapper.toEntity(dto);
        stadium.setOwner(owner);
        stadium.setRegion(region);
        stadium.setDistrict(district);
        var saved =  stadiumRepository.save(stadium);
        var response = mapper.toDto(saved);
        response.setOwnerName(owner.getUsername());
        return response;
    }

    @Override
    public StadiumResponseDto update(Long id, StadiumRequestDto dto) {
        var entity = getFindById(id);
        var owner = userRepository.findById(dto.getOwnerId())
          .orElseThrow(() -> new NotFoundException("owner_not_found",List.of(dto.getOwnerId().toString())));
        mapper.updateEntity(entity, dto);

        StadiumEntity updated = stadiumRepository.save(entity);
        var response = mapper.toDto(updated);
        response.setOwnerName(owner.getUsername());
        return response;
    }

    private StadiumEntity getFindById(Long id) {
        return stadiumRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("stadium_not_found",List.of(id.toString())));
    }

    @Override
    public void delete(Long id) {
        stadiumRepository.deleteById(id);
    }

    @Override
    public List<StadiumResponseDto> getById(Long id, LocalDate date, StadiumDuration duration) {
      // 1) Asl stadionni topamiz (unda ownerId bor)
      var stadium = getFindById(id);

      // 2) OwnerId bo‘yicha barcha stadionlarni olish
      var filter = new StadiumFilterParams();
      filter.setOwnerId(stadium.getOwner().getId());
      List<StadiumResponseDto> stadiums = stadiumRepository.getByOwnerId(filter);

      // 3) Har bir stadion uchun slotlar generatsiya qilamiz
      return stadiums.stream()
          .map(st -> buildStadiumResponseWithSlots(st, date, duration))
          .toList();
    }

    @Override
    public Page<StadiumResponseDto> getAll(StadiumFilterParams filterParams, Pageable pageable) {
      return stadiumRepository.getDistinctByOwner(filterParams,
          pageable);
    }

  @Override
  public StadiumResponseDto updateFavorite(Long id, Boolean isFavorite) {
      var stadion = getFindById(id);
      stadion.setIsFavorite(isFavorite);
    return mapper.toDto(stadiumRepository.save(stadion));
  }

  private StadiumResponseDto buildStadiumResponseWithSlots(StadiumResponseDto stadium,
      LocalDate date,
      StadiumDuration duration) {

    LocalDateTime dayStart = date.atStartOfDay();
    LocalDateTime dayEnd = date.plusDays(1).atStartOfDay();

    // Bookinglarni olish
    List<BookingEntity> bookings = bookingRepository
        .findAllByStadiumIdAndStartTimeBetween(stadium.getId(), dayStart, dayEnd);

    bookings.sort(Comparator.comparing(BookingEntity::getStartTime));

    // Band bo‘lmagan vaqtlarni topish
    List<TimeRange> freeRanges = calculateFreeRanges(dayStart, dayEnd, bookings);

    // Duration bo‘yicha bo‘sh slotlar yasash
    List<LocalDateTime> slots = generateAvailableSlots(freeRanges, duration.getMinutes());

    // Responsega yig‘ish
    stadium.setSlots(slots);

    return stadium;
  }

  private List<TimeRange> calculateFreeRanges(LocalDateTime dayStart,
      LocalDateTime dayEnd,
      List<BookingEntity> bookings) {

    List<TimeRange> free = new ArrayList<>();
    LocalDateTime current = dayStart;

    for (BookingEntity b : bookings) {
      if (b.getStartTime().isAfter(current)) {
        free.add(new TimeRange(current, b.getStartTime()));
      }
      if (b.getEndTime().isAfter(current)) {
        current = b.getEndTime();
      }
    }
    if (current.isBefore(dayEnd)) {
      free.add(new TimeRange(current, dayEnd));
    }

    return free;
  }

  private List<LocalDateTime> generateAvailableSlots(List<TimeRange> freeRanges, int durationMinutes) {
    List<LocalDateTime> slots = new ArrayList<>();

    for (TimeRange range : freeRanges) {
      LocalDateTime start = range.start();
      while (!start.plusMinutes(durationMinutes).isAfter(range.end())) {
        slots.add(start);
        start = start.plusMinutes(durationMinutes);
      }
    }
    return slots;
  }

}
