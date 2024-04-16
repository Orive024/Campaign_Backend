package com.orive.security.volunteers;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface VolunteersRepository extends JpaRepository<Volunteers, Long>{

	
//	//query to exist by name and email
//    boolean existsByNameAndEmail(String name, String email);

  //query to find Volunteer By Name
    @Query("SELECT v FROM Volunteers v WHERE v.name = :name")
    Optional<Volunteers> findByVolunteerName(@Param("name") String name);

  //query to find Volunteer By Name And phone
    @Query("SELECT v FROM Volunteers v WHERE v.name = :name AND v.phone = :phone")
    Optional<Volunteers> findByNameAndPhone(@Param("name") String name, @Param("phone") Long phone);
    
 // Custom query method to delete by volunteer phone
    @Modifying
    @Transactional
    @Query("DELETE FROM Volunteers v WHERE v.phone = :phone")
    void deleteByPhone(@Param("phone") Long phone);
}
