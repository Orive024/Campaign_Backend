package com.orive.security.gpstrack;



import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.orive.security.campaignDetails.ResourceNotFoundException;

@Service
public class FeatureService {

	private static final Logger logger=LoggerFactory.getLogger(FeatureService.class);

    @Autowired
    private FeatureRepository featureRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    //Save Features
    public Feature saveFeature(Feature feature) {
        return featureRepository.save(feature);
    }
    
    
    // Read all Features
    public List<Feature> getAllFeatures() {
        List<Feature> features = featureRepository.findAll();
        return features;
    }
    
    
// // Get feature by FeaturesId
//    public Feature getByFeaturesId(Long featuresid) {
//        // Get feature from database with the help of FeatureRepository
//        Feature feature = featureRepository.findById(featuresid)
//                .orElseThrow(() -> new ResourceNotFoundException("Feature with ID " + featuresid + " not found."));
//
//        try {
//            // Make a REST API call to retrieve feature collections for the feature
//            ResponseEntity<List<FeatureCollection>> responseEntity = restTemplate.exchange(
//                    "http://localhost:6060/api/featurecollection?featuresid=" + featuresid,
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<List<FeatureCollection>>() {}
//            );
//
//            if (responseEntity.getStatusCode() == HttpStatus.OK) {
//                List<FeatureCollection> featureCollections = responseEntity.getBody();
//                feature.setFeatureCollections(featureCollections);
//            } else {
//                // Handle unsuccessful API call response
//                throw new ResourceNotFoundException("Failed to retrieve feature collections for Feature with ID " + featuresid);
//            }
//        } catch (RestClientException e) {
//            // Handle REST client exception
//            throw new ResourceNotFoundException("Error while fetching feature collections: " + e.getMessage());
//        }
//
//        return feature;
//    }
    
    //get by AddBankId
    public Feature getByFeaturesId(Long featuresid) {
        //get user from database with the help  of user repository
    	Feature user = featureRepository.findById(featuresid).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + featuresid));
        ArrayList<FeatureCollection> ratingsOfUser = restTemplate.getForObject("http://localhost:7070/api/featurecollection/features/" + user.getFeaturesid(), ArrayList.class);
        logger.info("{} ", ratingsOfUser);
        user.setFeatureCollections(ratingsOfUser);
        return user;
    }

    
    
}
