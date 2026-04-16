package com.example.bron.tournament;

import com.example.bron.tournament.dto.TournamentResponseDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<TournamentEntity, Long> {

  @Query("""
  select new com.example.bron.tournament.dto.TournamentResponseDto(
  t.id,
  t.name,
  t.organizer.id,
  t.startDate,
  t.endDate,
  t.sportType,
  t.maxTeams,
  (select cast(count(tt) as long) from TournamentTeamEntity tt where tt.tournament = t),
  t.entryFee,
  t.rules,
  t.status,
  t.startTime,
  t.endTime,
  t.location,
  t.address,
  t.prizes
  ) from TournamentEntity t
  where (:#{#filterParams.name} is null or t.name ilike %:#{#filterParams.name}%)
  and (:#{#filterParams.organizerId} is null or t.organizer.id = :#{#filterParams.organizerId})
  and (:#{#filterParams.startDateFromIsNull} = TRUE or t.startDate >= :#{#filterParams.startDateFrom})
  and (:#{#filterParams.startDateToIsNull} = TRUE or t.startDate <= :#{#filterParams.startDateTo})
  and (:#{#filterParams.sportTypeIsNull} = TRUE or t.sportType = :#{#filterParams.sportType})
  and (:#{#filterParams.maxTeams} is null or t.maxTeams = :#{#filterParams.maxTeams})
  and (:#{#filterParams.maxEntryFee} is null or t.entryFee <= :#{#filterParams.maxEntryFee})
  and (:#{#filterParams.statusIsNull} = TRUE or t.status = :#{#filterParams.status})
  and (:#{#filterParams.address} is null or t.address ilike %:#{#filterParams.address}%)
  order by t.id desc
  """)
  List<TournamentResponseDto> getAll(TournamentFilterParams filterParams);

  List<TournamentEntity> findByStartDate(LocalDate startDate);

}
