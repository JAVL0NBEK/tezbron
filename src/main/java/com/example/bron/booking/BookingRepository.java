package com.example.bron.booking;

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
  AND b.startTime < :end
  AND b.endTime > :start
  """)
  List<BookingEntity> findConflictingBookings(Long stadiumId,
      LocalDateTime start,
      LocalDateTime end);

  @Query("""
  SELECT b FROM BookingEntity b
  WHERE b.stadium.id IN :stadiumIds
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
  AND cast(b.startTime as date) = :date
  """)
  List<BookingEntity> findIdAndDateBookings(Long stadiumId,
      LocalDate date);
}
