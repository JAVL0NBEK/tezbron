package com.example.bron.location;

import com.example.bron.location.dto.DistrictResponseDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {

  @Query("""
  select new com.example.bron.location.dto.DistrictResponseDto(
    d.id,
    d.name,
    d.shortName
    ) from DistrictEntity d
      where d.region.id = :regionId
  """)
  List<DistrictResponseDto> findDistrictByRegionId(Long regionId);
}
