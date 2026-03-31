package com.example.bron.team;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

  @Query("select distinct t from TeamEntity t left join fetch t.members")
  List<TeamEntity> findAllWithMembers();

}
