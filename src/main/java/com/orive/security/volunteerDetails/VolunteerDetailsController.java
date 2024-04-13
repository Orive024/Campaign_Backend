package com.orive.security.volunteerDetails;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;





@RestController
@RequestMapping(value = "volunteerdetails")
@CrossOrigin(origins = "*")
public class VolunteerDetailsController {
	
	
private static final Logger logger = LoggerFactory.getLogger(VolunteerDetailsController.class);
	
	@Autowired
	private VolunteerDetailsService volunteerDetailsService;

	
	 @PostMapping("/create")
	    public ResponseEntity<VolunteerDetailsDto> createVolunteerDetails(@RequestBody VolunteerDetailsDto volunteerDetailsDto) {
	        logger.info("Received request to create VolunteerDetails for Volunteername: {} and Emailaddress: {}", volunteerDetailsDto.getVolunteername(), volunteerDetailsDto.getEmailaddress());

	        VolunteerDetailsDto createdVolunteerDetails = volunteerDetailsService.createsVolunteerDetails(volunteerDetailsDto);
	        if (createdVolunteerDetails != null) {
	            logger.info("VolunteerDetails created successfully with ID: {}", createdVolunteerDetails.getVolunteerid());
	            return new ResponseEntity<>(createdVolunteerDetails, HttpStatus.CREATED);
	        } else {
	            logger.warn("VolunteerDetails already exists for Volunteername: {} and Emailaddress: {}", volunteerDetailsDto.getVolunteername(), volunteerDetailsDto.getEmailaddress());
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	    }

    
    
//upload excelsheet   
    @PostMapping("/excelfile/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file)
	{
		if(VolunteerExcelHelper.chechExcelFormat(file))
		{
			//true
			this.volunteerDetailsService.save(file);
			return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to database"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please upload excel file ");
	}
	

    //get all excelsheet
	@GetMapping("/get/excelfile")
	public List<VolunteerDetails> getAllVolunteerDetails()
	{
		return this.volunteerDetailsService.getAllVolunteerDetails();
	}

    

    // Get all VolunteerDetails   
    @GetMapping("/get/volunteerdetails")
    public ResponseEntity<List<VolunteerDetailsDto>> getAllVolunteerDetail() {
        List<VolunteerDetailsDto> volunteerDetails = volunteerDetailsService.getAllVolunteerDetail();
        logger.info("Retrieved {} VolunteerDetails from the database", volunteerDetails.size());
        return new ResponseEntity<>(volunteerDetails, HttpStatus.OK);
    }

    
    //get VolunteerDetails by VolunteerId
 	  @GetMapping("/volunteerdetails/{volunteerid}")
 	    public ResponseEntity<List<VolunteerDetailsDto>>  getVolunteerDetailsByVolunteerId(@PathVariable String volunteerid) {
 	        List<VolunteerDetailsDto> volunteer = volunteerDetailsService.getVolunteerDetailsByVolunteerId(volunteerid);

 	        if (volunteer.isEmpty()) {
 	            return ResponseEntity.notFound().build();
 	        } else {
 	            return ResponseEntity.ok(volunteer);
 	        }
 	    }
    
    
	  
 	 //get VolunteerDetails by  Volunteername And Mobilenumber
    @GetMapping("/get/name/{volunteername}/{mobilenumber}")
    public ResponseEntity<VolunteerDetailsDto> getVolunteerDetailsByNameAndMobileNumber(@PathVariable String volunteername, @PathVariable Long mobilenumber) {
        Optional<VolunteerDetailsDto> volunteer = volunteerDetailsService.getVolunteerDetailsByNameAndMobileNumber(volunteername,mobilenumber);
        if (volunteer.isPresent()) {
            logger.info("Retrieved VolunteerDetails with  Volunteername And Mobilenumber: {}", volunteername,mobilenumber);
            return new ResponseEntity<>(volunteer.get(), HttpStatus.OK);
        } else {
            logger.warn("VolunteerDetails with Volunteername And Mobilenumber {} not found", volunteername,mobilenumber);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    

    // Update VolunteerDetails by VolunteerID
    @PutMapping("/update/{volunteerid}")
    public ResponseEntity<VolunteerDetailsDto> updateVolunteerDetails(@PathVariable String volunteerid, @RequestBody VolunteerDetailsDto updatedVolunteerDetailsDto) {
    	VolunteerDetailsDto updatedVolunteerDetails = volunteerDetailsService.updateVolunteerDetails(volunteerid, updatedVolunteerDetailsDto);
        if (updatedVolunteerDetails != null) {
            logger.info("Updated VolunteerDetails with VolunteerID: {}", volunteerid);
            return new ResponseEntity<>(updatedVolunteerDetails, HttpStatus.OK);
        } else {
            logger.warn("VolunteerDetails with VolunteerID {} not found for update", volunteerid);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    // Update list by Volunteername And Mobilenumber
    @PutMapping("/update/{volunteername}/{mobilenumber}")
    public ResponseEntity<VolunteerDetailsDto> updateVolunteerDetailsByVolunteernameAndMobilenumber(@PathVariable String volunteername, @PathVariable Long mobilenumber, @RequestBody VolunteerDetailsDto updatedVolunteerDetailsDto) {
    	VolunteerDetailsDto updatedVolunteerDetails = volunteerDetailsService.updateVolunteerDetailsByVolunteernameAndMobilenumber(volunteername, mobilenumber, updatedVolunteerDetailsDto);
        if (updatedVolunteerDetails != null) {
            logger.info("Updated VolunteerDetails with Volunteername and Mobilenumber: {}", volunteername,mobilenumber);
            return new ResponseEntity<>(updatedVolunteerDetails, HttpStatus.OK);
        } else {
            logger.warn("VolunteerDetails with Volunteername and Mobilenumber {} not found for update", volunteername,mobilenumber);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
   
    
    
//    // Delete VolunteerDetails by VolunteerID  
//    @DeleteMapping("/delete/{volunteerid}")
//    public ResponseEntity<Void> deleteVolunteerDetails(@PathVariable String volunteerid) {
//        volunteerDetailsService.deleteVolunteerDetails(volunteerid);
//        logger.info("Deleted VolunteerDetails with VolunteerID: {}", volunteerid);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
    
 // Delete VolunteerDetails by VolunteerID  
    @DeleteMapping("/delete/{volunteerid}")
    public ResponseEntity<Void> deleteVolunteerDetails(@PathVariable String volunteerid) {
        volunteerDetailsService.deleteVolunteerDetails(volunteerid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	    
    //count the total VolunteerDetails
	    @GetMapping("/count/volunteerdetails")
	    public long countVolunteerDetails()
	    {
	    	return volunteerDetailsService.countVolunteerDetails();
	    }

}
