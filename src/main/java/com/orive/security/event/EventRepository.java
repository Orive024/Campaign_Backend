package com.orive.security.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	
	@Query("SELECT COUNT(e) FROM Event e")
    Long countAllEvents();
}

