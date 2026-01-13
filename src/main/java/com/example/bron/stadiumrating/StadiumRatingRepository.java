package com.example.bron.stadiumrating;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRatingRepository extends JpaRepository<StadiumRatingEntity, Long> {

  StadiumRatingEntity findByUserIdAndStadiumId(Long userId, Long stadiumId);

  @Query("""
    select avg(r.rating) from StadiumRatingEntity r
    where r.stadium.id = :stadiumId
""")
  Double getAverageRating(Long stadiumId);

  @Query("""
    select count(r) from StadiumRatingEntity r
    where r.stadium.id = :stadiumId
""")
  Long getRatingCount(Long stadiumId);

  List<StadiumRatingEntity> findAllByStadiumId(Long stadiumId);

}