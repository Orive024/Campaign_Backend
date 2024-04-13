package com.orive.security.gpstrack;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureCollectionService {

	@Autowired
    private FeatureCollectionRepository featureCollectionRepository;
	
	//Create FeatureCollection
	 public FeatureCollection saveFeatureCollection(FeatureCollection featureCollection) {
	        return featureCollectionRepository.save(featureCollection);
	    }
	 
	 //Get All FeatureCollection
	   public List<FeatureCollection> getAllFeatureCollection() {
	        return featureCollectionRepository.findAll();
	    }

	   //Get By FeatureCollection By ID
	    public Optional<FeatureCollection> getFeatureCollectionById(Long collecionid) {
	        return featureCollectionRepository.findById(collecionid);
	    }
		

	    // Get FeatureCollection by Featuresid
	    public List<FeatureCollection> getFeatureCollectionByFeaturesId(Long featuresid) {
	        List<FeatureCollection> featureCollections = featureCollectionRepository.findByFeatureId(featuresid);
	        return featureCollections;
	    }

	    
}
