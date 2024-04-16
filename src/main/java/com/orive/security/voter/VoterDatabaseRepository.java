package com.orive.security.voter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.orive.security.volunteers.Volunteers;


public interface VoterDatabaseRepository extends JpaRepository<VoterDatabase, Long>{

	
//query to find VoterDatabase By voter_id
    @Query("SELECT v FROM VoterDatabase v WHERE v.voter_id = :voter_id")
    List<VoterDatabase> findByVoterId(@Param("voter_id") String voter_id);

//query to find VoterDatabase By Name
  @Query("SELECT v FROM VoterDatabase v WHERE v.name = :name")
  Optional<VoterDatabase> findByVoterName(@Param("name") String name);

//query to find VoterDatabase By Name And phone
  @Query("SELECT v FROM VoterDatabase v WHERE v.name = :name AND v.phone = :phone")
  Optional<VoterDatabase> findByNameAndPhone(@Param("name") String name, @Param("phone") Long phone);
  
// Custom query method to delete by volunteer voter_id
  @Modifying
  @Transactional
  @Query("DELETE FROM VoterDatabase v WHERE v.voter_id = :voter_id")
  void deleteByVoterId(@Param("voter_id") String voter_id);
}
