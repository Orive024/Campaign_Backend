package com.orive.security.voterdatabase;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface VoterDatabaseListRepository extends JpaRepository<VoterDatabaseList, Long>{

	
	//query to find Voter By Pincode
    @Query("SELECT v FROM VoterDatabaseList v WHERE v.pincode = :pincode")
    List<VoterDatabaseList> findByPincode(@Param("pincode") String pincode);
}
