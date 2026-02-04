package com.example.bron.stadium;

import com.example.bron.stadium.dto.StadiumResponseDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository extends JpaRepository<StadiumEntity, Long> {

  @Query("""
    select new com.example.bron.stadium.dto.StadiumResponseDto(
        s.id,
        s.name,
        s.owner.id,
        s.owner.fullName,
        s.description,
        s.location,
        s.type,
        s.duration,
        s.capacity,
        s.pricePerHour,
        s.isActive,
        s.region.name,
        s.district.name
    )
    from StadiumEntity s
    
    where s.id in (
        select min(st.id) from StadiumEntity st group by st.owner.id
    )
    and (:#{#filterParams.id} is null or s.id = :#{#filterParams.id})
    and (:#{#filterParams.capacity} is null or s.capacity = :#{#filterParams.capacity})
    and (:#{#filterParams.name} is null or s.name ilike %:#{#filterParams.name}%)
    and (:#{#filterParams.ownerName} is null or s.owner.fullName ilike %:#{#filterParams.ownerName}%)
    and (:#{#filterParams.ownerId} is null or s.owner.id = :#{#filterParams.ownerId})
    and (:#{#filterParams.capacity} is null or s.capacity = :#{#filterParams.capacity})
    and (:#{#filterParams.pricePerHour} is null or s.pricePerHour = :#{#filterParams.pricePerHour})
    and (:#{#filterParams.isActive} is null or s.isActive = :#{#filterParams.isActive})
    and (:#{#filterParams.description} is null or s.description ilike %:#{#filterParams.description}%)
    and (:#{#filterParams.stadiumTypeIsNull} = TRUE or s.type = :#{#filterParams.type})
    and (:#{#filterParams.stadiumDurationIsNull} = TRUE or s.duration = :#{#filterParams.duration})
    
""")
  Page<StadiumResponseDto> getDistinctByOwner(StadiumFilterParams filterParams,
      Pageable pageable);

  @Query("""
    select new com.example.bron.stadium.dto.StadiumResponseDto(
        s.id,
        s.name,
        s.owner.id,
        s.owner.fullName,
        s.description,
        s.location,
        s.type,
        s.duration,
        s.capacity,
        s.pricePerHour,
        s.isActive,
        s.region.name,
        s.district.name
    )
    from StadiumEntity s
    where (:#{#filterParams.id} is null or s.id = :#{#filterParams.id})
    and (:#{#filterParams.capacity} is null or s.capacity = :#{#filterParams.capacity})
    and (:#{#filterParams.name} is null or s.name ilike %:#{#filterParams.name}%)
    and (:#{#filterParams.ownerName} is null or s.owner.fullName ilike %:#{#filterParams.ownerName}%)
    and (:#{#filterParams.ownerId} is null or s.owner.id = :#{#filterParams.ownerId})
    and (:#{#filterParams.capacity} is null or s.capacity = :#{#filterParams.capacity})
    and (:#{#filterParams.pricePerHour} is null or s.pricePerHour = :#{#filterParams.pricePerHour})
    and (:#{#filterParams.isActive} is null or s.isActive = :#{#filterParams.isActive})
    and (:#{#filterParams.description} is null or s.description ilike %:#{#filterParams.description}%)
    and (:#{#filterParams.stadiumTypeIsNull} = TRUE or s.type = :#{#filterParams.type})
    and (:#{#filterParams.stadiumDurationIsNull} = TRUE or s.duration = :#{#filterParams.duration})
    
""")
  List<StadiumResponseDto> getByOwnerId(StadiumFilterParams filterParams);

}
