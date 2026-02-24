package com.example.bron.booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
}
