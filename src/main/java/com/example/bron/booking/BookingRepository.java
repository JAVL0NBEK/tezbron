package com.example.bron.booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

  Optional<BookingEntity> findByStadiumId(Long id);

  List<BookingEntity> findAllByStadiumIdAndStartTimeBetween(Long stadiumId, LocalDateTime start, LocalDateTime end);
}
