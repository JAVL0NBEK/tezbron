package com.example.bron.dashboard;

import com.example.bron.booking.BookingEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends JpaRepository<BookingEntity, Long> {

  @Query("""
      select new com.example.bron.dashboard.StadiumRevenueDto(
          b.stadium.id, sum(b.totalPrice)
      )
      from BookingEntity b
      group by b.stadium.id
      """)
  List<StadiumRevenueDto> sumTotalPriceByStadium();

  @Query("select count(t) from TournamentEntity t")
  long countTournaments();

  @Query("select count(s) from StadiumEntity s where s.isActive = true")
  long countActiveStadiums();

  @Query("select count(u) from UserEntity u")
  long countUsers();

  @Query("""
      select count(b) from BookingEntity b
      where b.createdAt >= :start and b.createdAt < :end
      """)
  long countBookingsBetween(@Param("start") LocalDateTime start,
                            @Param("end") LocalDateTime end);

  @Query("""
      select coalesce(sum(b.totalPrice), 0)
      from BookingEntity b
      where b.createdAt >= :start and b.createdAt < :end
      """)
  Double sumRevenueBetween(@Param("start") LocalDateTime start,
                           @Param("end") LocalDateTime end);

  @Query("""
      select new com.example.bron.dashboard.DailyRevenueDto(
          cast(b.createdAt as LocalDate), sum(b.totalPrice)
      )
      from BookingEntity b
      where b.createdAt >= :start and b.createdAt < :end
      group by cast(b.createdAt as LocalDate)
      order by cast(b.createdAt as LocalDate)
      """)
  List<DailyRevenueDto> dailyRevenueBetween(@Param("start") LocalDateTime start,
                                            @Param("end") LocalDateTime end);
}
