package com.orive.security.areas;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "areas")
@CrossOrigin(origins = "*")
public class AreaController {

	private static final Logger logger = LoggerFactory.getLogger(AreaController.class);
	
	@Autowired
	private AreasService areasService;
	
	//Create a Areas
	   @PostMapping("/create/areas")
	    public ResponseEntity<Areas> createAreas(@RequestBody Areas area) {
	        logger.info("Creating Areas: {}", area);
	        Areas createdArea = areasService.createAreas(area);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdArea);
	    }
	   
	   
	// Get all Areas
	    @GetMapping("/getAll/areas")
	    public ResponseEntity<List<Areas>> getAllAreas() {
	        logger.info("Fetching all areas");
	        List<Areas> areasList = areasService.getAllAreas();
	        return ResponseEntity.ok(areasList);
	    }

	    // Get Areas by ID
	    @GetMapping("/get/{areasId}")
	    public ResponseEntity<Areas> getAreaById(@PathVariable Long areasId) {
	        logger.info("Fetching area by ID: {}", areasId);
	        Optional<Areas> area = areasService.getAreasById(areasId);
	        return area
	        		.map(ResponseEntity::ok)
	        		.orElse(ResponseEntity
	        		.notFound()
	        		.build());
	    }

	    // Update Areas
	    @PutMapping("/update/{areasId}")
	    public ResponseEntity<Areas> updateArea(@PathVariable Long areasId, @RequestBody Areas updatedArea) {
	        logger.info("Updating area with ID {}: {}", areasId, updatedArea);
	        Areas area = areasService.updateAreasById(areasId, updatedArea);
	        return ResponseEntity.ok(area);
	    }
	    
	    //Count the Total Areas   
	    @GetMapping("/count/areas")
	    public long countAreas()
	    {
	    	return areasService.countAreas();
	    }
	

	    // Delete Areas
	    @DeleteMapping("/delete/{areasId}")
	    public ResponseEntity<Void> deleteArea(@PathVariable Long areasId) {
	        logger.info("Deleting area with ID: {}", areasId);
	        areasService.deleteAreas(areasId);
	        return ResponseEntity.noContent().build();
	    }    
	    
}
