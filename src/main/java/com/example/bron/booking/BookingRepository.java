package com.example.bron.booking;

import com.example.bron.booking.dto.BookingResponseDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
  @Query("""
  SELECT b FROM BookingEntity b
  WHERE b.stadium.id = :stadiumId
  AND b.status <> com.example.bron.enums.BookingStatus.CANCELLED
  AND b.startTime < :end
  AND b.endTime > :start
  """)
  List<BookingEntity> findConflictingBookings(Long stadiumId,
      LocalDateTime start,
      LocalDateTime end);

  @Query("""
  SELECT b FROM BookingEntity b
  WHERE b.stadium.id IN :stadiumIds
  AND b.status <> com.example.bron.enums.BookingStatus.CANCELLED
  AND b.startTime < :end
  AND b.endTime > :start
  """)
  List<BookingEntity> findConflictingBookingsForStadiums(
      @Param("stadiumIds") List<Long> stadiumIds,
      @Param("start") LocalDateTime start,
      @Param("end") LocalDateTime end);

  @Query("""
    select case when count(b) > 0 then true else false end
    from BookingEntity b
    where b.stadium.id = :stadiumId
      and b.status <> com.example.bron.enums.BookingStatus.CANCELLED
      and :startTime < b.endTime
      and :endTime > b.startTime
""")
  boolean existsOverlappingBooking(
      @Param("stadiumId") Long stadiumId,
      @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime
  );

  @Query("""
  SELECT b FROM BookingEntity b
  WHERE b.stadium.id = :stadiumId
  AND b.status <> com.example.bron.enums.BookingStatus.CANCELLED
  AND cast(b.startTime as date) = :date
  """)
  List<BookingEntity> findIdAndDateBookings(Long stadiumId,
      LocalDate date);

  @Query("""
  SELECT b FROM BookingEntity b
  WHERE b.startTime BETWEEN :from AND :to
  """)
  List<BookingEntity> findUpcomingBookings(LocalDateTime from, LocalDateTime to);

  @Query("""
  select new com.example.bron.booking.dto.BookingResponseDto(
  b.id,
  b.user.id,
  b.stadium.id,
  b.match.id,
  b.startTime,
  b.endTime,
  b.totalPrice,
  b.status,
  b.paymentMethod
  ) from BookingEntity b
  where (:#{#filterParams.userId} is null or b.user.id = :#{#filterParams.userId})
  and (:#{#filterParams.stadiumId} is null or b.stadium.id = :#{#filterParams.stadiumId})
  and (:#{#filterParams.matchId} is null or b.match.id = :#{#filterParams.matchId})
  and (:#{#filterParams.startDateFromIsNull} = TRUE or cast(b.startTime as date) >= :#{#filterParams.startDateFrom})
  and (:#{#filterParams.startDateToIsNull} = TRUE or cast(b.startTime as date) <= :#{#filterParams.startDateTo})
  and (:#{#filterParams.totalPrice} is null or b.totalPrice = :#{#filterParams.totalPrice})
  and (:#{#filterParams.statusIsNull} = TRUE or b.status = :#{#filterParams.status})
  and (:#{#filterParams.paymentMethod} is null or b.paymentMethod ilike %:#{#filterParams.paymentMethod}%)
  order by b.id desc
  """)
  List<BookingResponseDto> getAll(BookingFilterParams filterParams);
}
