package com.orive.security.gpstrack;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/feature")
@Tag(name = "Feature")
@CrossOrigin(origins = "*")
public class FeatureController {

	
	    @Autowired
	    private FeatureService featureService;

	    
	    //Create Feature
	    @PostMapping("/create")
	    public Feature createFeature(@RequestBody Feature feature) {
	        return featureService.saveFeature(feature);
	    }
	    
	 // Read all features
	    @GetMapping("/getAll")
	    public List<Feature> getAllFeatures() {
	        return featureService.getAllFeatures();
	    }

	    // Get feature by FeaturesId
	    @GetMapping("/{featuresid}")
	    public Feature getFeatureById(@PathVariable Long featuresid) {
	        return featureService.getByFeaturesId(featuresid);
	    }
}
