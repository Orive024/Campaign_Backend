package com.orive.security.gpstrack;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeatureCollectionRepository extends JpaRepository<FeatureCollection, Long>{

	@Query("SELECT f FROM FeatureCollection f WHERE f.featuresid = :featuresid")
	List<FeatureCollection> findByFeatureId(Long featuresid);
}
