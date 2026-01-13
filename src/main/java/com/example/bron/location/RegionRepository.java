package com.example.bron.location;

import com.example.bron.location.dto.RegionResponseDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Long> {
  @Query("""
   select new com.example.bron.location.dto.RegionResponseDto(
   r.id,
   r.name,
   r.shortName
   ) from RegionEntity r
""")
  List<RegionResponseDto> getAll();
}
