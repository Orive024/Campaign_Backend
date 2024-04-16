package com.orive.security.voter;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface VoterRepository extends JpaRepository<Voter, Long>{

	
	//query to find Voter By Pincode
    @Query("SELECT v FROM Voter v WHERE v.pincode = :pincode")
    List<Voter> findByPincode(@Param("pincode") String pincode);
    
   
}
