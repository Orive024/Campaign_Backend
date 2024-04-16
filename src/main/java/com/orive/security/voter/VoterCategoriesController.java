package com.orive.security.voter;

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
@RequestMapping(value = "votercategories")
@CrossOrigin(origins = "*")
public class VoterCategoriesController {

	
private static final Logger logger = LoggerFactory.getLogger(VoterCategoriesController.class);
	
	@Autowired
	private VoterCategoriesService voterCategoriesService;
	
	//Create a VoterCategories
	   @PostMapping("/create/votercategories")
	    public ResponseEntity<VoterCategories> createVoterCategories(@RequestBody VoterCategories voterCategories) {
	        logger.info("Creating VoterCategories: {}", voterCategories);
	        VoterCategories createdVoterCategories = voterCategoriesService.createVoterCategories(voterCategories);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdVoterCategories);
	    }
	   
	   
	// Get all VoterCategories
	    @GetMapping("/getAll/votercategories")
	    public ResponseEntity<List<VoterCategories>> getAllVoterCategories() {
	        logger.info("Fetching all VoterCategories");
	        List<VoterCategories> voterCategories = voterCategoriesService.getAllVoterCategories();
	        return ResponseEntity.ok(voterCategories);
	    }

	    // Get VoterCategories by ID
	    @GetMapping("/get/{sl}")
	    public ResponseEntity<VoterCategories> getVoterCategoriesById(@PathVariable Long sl) {
	        logger.info("Fetching VoterCategories by ID: {}", sl);
	        Optional<VoterCategories> voterCategories = voterCategoriesService.getVoterCategoriesById(sl);
	        return voterCategories
	        		.map(ResponseEntity::ok)
	        		.orElse(ResponseEntity
	        		.notFound()
	        		.build());
	    }

	    // Update VoterCategories
	    @PutMapping("/update/{sl}")
	    public ResponseEntity<VoterCategories> updateVoterCategories(@PathVariable Long sl, @RequestBody VoterCategories updatedVoterCategories) {
	        logger.info("Updating VoterCategories with ID {}: {}", sl, updatedVoterCategories);
	        VoterCategories voterCategories = voterCategoriesService.updateVoterCategoriesById(sl, updatedVoterCategories);
	        return ResponseEntity.ok(voterCategories);
	    }
	    
	    //Count the Total VoterCategories   
	    @GetMapping("/count/votercategories")
	    public long countVoterCategories()
	    {
	    	return voterCategoriesService.countVoterCategories();
	    }
	

	    // Delete VoterCategories
	    @DeleteMapping("/delete/{sl}")
	    public ResponseEntity<Void> deleteVoterCategories(@PathVariable Long sl) {
	        logger.info("Deleting VoterCategories with ID: {}", sl);
	        voterCategoriesService.deleteVoterCategories(sl);
	        return ResponseEntity.noContent().build();
	    }    
}
