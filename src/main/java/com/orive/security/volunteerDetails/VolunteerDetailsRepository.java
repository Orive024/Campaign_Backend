package com.orive.security.volunteerDetails;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface VolunteerDetailsRepository extends JpaRepository<VolunteerDetails, Long>{

	
	//query to exist by Volunteername and Emailaddress
    boolean existsByVolunteernameAndEmailaddress(String volunteername, String emailaddress);

  //query to find VolunteerDetails By Volunteerid
    @Query("SELECT v FROM VolunteerDetails v WHERE v.volunteerid = :volunteerid")
    List<VolunteerDetails> findByVolunteerId(@Param("volunteerid") String volunteerid);

  //query to find VolunteerDetails By Volunteername And Mobilenumber
    @Query("SELECT v FROM VolunteerDetails v WHERE v.volunteername = :volunteername AND v.mobilenumber = :mobilenumber")
    Optional<VolunteerDetails> findByVolunteernameAndMobilenumber(@Param("volunteername") String volunteername, @Param("mobilenumber") Long mobilenumber);
    
 // Custom query method to delete by volunteer ID
    @Modifying
    @Transactional
    @Query("DELETE FROM VolunteerDetails v WHERE v.volunteerid = :volunteerid")
    void deleteByVolunteerId(@Param("volunteerid") String volunteerid);
}
