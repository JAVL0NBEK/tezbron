package com.example.bron.match;

import com.example.bron.match.dto.MatchResponseDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity,Long> {
  @Query("""
  select new com.example.bron.match.dto.MatchResponseDto(
  match.id,
  match.title,
  match.organizer.id,
  match.stadium.id,
  match.dateTime,
  match.duration,
  match.maxPlayers,
  match.currentPlayers,
  match.pricePerPlayer,
  match.status,
  match.location
  ) from MatchEntity match
  where (:#{#filterParams.startDateFromIsNull} = TRUE OR cast(match.dateTime as date) >= :#{#filterParams.startDateFrom})
  AND (:#{#filterParams.startDateToIsNull} = TRUE OR cast(match.dateTime as date) <= :#{#filterParams.startDateTo})
  and (:#{#filterParams.regionId} is null or match.stadium.region.id = :#{#filterParams.regionId})
  and (:#{#filterParams.districtId} is null or match.stadium.district.id = :#{#filterParams.districtId})
""")
  List<MatchResponseDto> getAll(MatchFilterParams filterParams);

}
