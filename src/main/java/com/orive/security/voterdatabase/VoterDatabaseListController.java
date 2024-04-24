package com.orive.security.voterdatabase;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;




@RestController
@RequestMapping(value = "voterdatabaselist")
@CrossOrigin(origins = "*")
public class VoterDatabaseListController {

	
	private static final Logger logger = LoggerFactory.getLogger(VoterDatabaseListController.class);

    @Autowired
    private VoterDatabaseListService voterService;
    
    
 // Create a new Voter
    @PostMapping("/create/voter")
    public ResponseEntity<VoterdatabaseDtoList> createVoter(@RequestBody VoterdatabaseDtoList voterDto) {
    	VoterdatabaseDtoList createdVoter = voterService.createVoter(voterDto);
        logger.info("Created Voter with name: {}", createdVoter.getVotername());
        return new ResponseEntity<>(createdVoter, HttpStatus.CREATED);
    }
    
    
    //upload excelsheet   
    @PostMapping("/excelfile/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file)
	{
		if(VoterDatabaseListExcelHelper.chechExcelFormat(file))
		{
			//true
			this.voterService.save(file);
			return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to database"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please upload excel file ");
	}
	

    //get all excelsheet
	@GetMapping("/get/excelfile")
	public List<VoterDatabaseList> getAllVoters()
	{
		return this.voterService.getAllVoters();
	}


    // Get all Voter    
    @GetMapping("/get/voter")
    public ResponseEntity<List<VoterdatabaseDtoList>> getAllVoter() {
        List<VoterdatabaseDtoList> voters = voterService.getAllVoter();
        logger.info("Retrieved {} Voter from the database", voters.size());
        return new ResponseEntity<>(voters, HttpStatus.OK);
    }

    //get Voters by Pincode
	  @GetMapping("/voter/{pincode}")
	    public ResponseEntity<List<VoterdatabaseDtoList>>  getVotersByPincode(@PathVariable("pincode") String pincode) {
	        List<VoterdatabaseDtoList> voters = voterService.getVoterByPincode(pincode);

	        if (voters.isEmpty()) {
	            return ResponseEntity.notFound().build();
	        } else {
	            return ResponseEntity.ok(voters);
	        }
	    }

//    // Update Voters by ID
//    public ResponseEntity<VoterDto> updateVoters(@PathVariable Long sl, @RequestBody VoterDto updatedVoterDto) {
//    	VoterDto updatedVoters = voterService.updateVoters(sl, updatedVoterDto);
//        if (updatedVoters != null) {
//            logger.info("Updated Voters with ID: {}", sl);
//            return new ResponseEntity<>(updatedVoters, HttpStatus.OK);
//        } else {
//            logger.warn("Voters with ID {} not found for update", sl);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    

    // Delete Voters by ID
    @DeleteMapping("/delete/{sl}")
    public ResponseEntity<Void> deleteVoter(@PathVariable Long sl) {
    	voterService.deleteVoter(sl);
        logger.info("Deleted Voters with ID: {}", sl);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	    
    //Count Voters
	    @GetMapping("/count/voter")
	    public long countVoter()
	    {
	    	return voterService.countVoter();
	    }

}
