package com.orive.security.gpstrack;

import java.util.List;
import java.util.Optional;

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
@RequestMapping("/api/featurecollection")
@Tag(name = "FeatureCollection")
@CrossOrigin(origins = "*")
public class FeatureCollectionController {

	@Autowired
    private FeatureCollectionService featureCollectionService;

	//Create FeatureCollection
    @PostMapping("/create")
    public FeatureCollection createFeatureCollection(@RequestBody FeatureCollection collection) {
        return featureCollectionService.saveFeatureCollection(collection);
    }
    
    //Get All FeatureCollection
    @GetMapping("/all")
    public List<FeatureCollection> getAllFeatureCollections() {
        return featureCollectionService.getAllFeatureCollection();
    }

    //Get FeatureCollection By Id
    @GetMapping("/{collecionid}")
    public Optional<FeatureCollection> getFeatureCollectionById(@PathVariable("collecionid") Long collecionid) {
        return featureCollectionService.getFeatureCollectionById(collecionid);
    }

    //Get FeatureCollection By FeaturesId
    @GetMapping("/features/{featuresid}")
    public List<FeatureCollection> getFeatureCollectionByFeaturesId(@PathVariable("featuresid") Long featuresid) {
        return featureCollectionService.getFeatureCollectionByFeaturesId(featuresid);
    }
}
