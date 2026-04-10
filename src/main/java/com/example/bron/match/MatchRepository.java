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
  SIZE(match.participants),
  match.pricePerPlayer,
  match.status,
  match.location,
  match.sportType
  ) from MatchEntity match
  where (:#{#filterParams.title} is null or match.title ilike %:#{#filterParams.title}%)
  and (:#{#filterParams.organizerId} is null or match.organizer.id = :#{#filterParams.organizerId})
  and (:#{#filterParams.stadiumId} is null or match.stadium.id = :#{#filterParams.stadiumId})
  and (:#{#filterParams.regionId} is null or match.stadium.region.id = :#{#filterParams.regionId})
  and (:#{#filterParams.districtId} is null or match.stadium.district.id = :#{#filterParams.districtId})
  and (:#{#filterParams.startDateFromIsNull} = TRUE or cast(match.dateTime as date) >= :#{#filterParams.startDateFrom})
  and (:#{#filterParams.startDateToIsNull} = TRUE or cast(match.dateTime as date) <= :#{#filterParams.startDateTo})
  and (:#{#filterParams.durationIsNull} = TRUE or match.duration = :#{#filterParams.duration})
  and (:#{#filterParams.sportTypeIsNull} = TRUE or match.sportType = :#{#filterParams.sportType})
  and (:#{#filterParams.maxPlayers} is null or match.maxPlayers = :#{#filterParams.maxPlayers})
  and (:#{#filterParams.pricePerPlayer} is null or match.pricePerPlayer = :#{#filterParams.pricePerPlayer})
  and (:#{#filterParams.statusIsNull} = TRUE or match.status = :#{#filterParams.status})
  order by match.id desc
""")
  List<MatchResponseDto> getAll(MatchFilterParams filterParams);

}
