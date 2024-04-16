package com.orive.security.volunteers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@RequestMapping(value = "volunteers")
@CrossOrigin(origins = "*")
public class VolunteersController {
	
	
private static final Logger logger = LoggerFactory.getLogger(VolunteersController.class);
	
	@Autowired
	private VolunteersService volunteersService;

	
//=============================== Create a new Volunteers ==============================================================
	
    @PostMapping(value = "/create/volunteers" , consumes = "multipart/form-data")  
    public ResponseEntity<?> saveVolunteers(
  		  @RequestParam(value = "name", required = false)  String name,
  		  @RequestParam(value = "gender", required = false)String gender,
  		  @RequestParam(value = "email", required = false) String email,
  		  @RequestParam(value = "address", required = false) String address,
  		  @RequestParam(value = "areaname", required = false) String areaname,
  		  @RequestParam(value = "phone", required = false) Long phone,
  		  @RequestParam(value = "password", required = false) String password,
  		  @RequestParam(value = "profile", required = false)  String profile, 		  		 
  		  @RequestParam(value = "image_upload", required = false) MultipartFile image_upload
           ) {
  	  String result = volunteersService.saveVolunteers(name, gender, email, address, areaname, phone, password, profile, image_upload);
  			  
    
  	  if (result != null && result.startsWith("Error")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to save Volunteers", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
  
  
  
//===================== Get Volunteers logo by name ==================================================================
    @GetMapping("/{name}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String name) {
        byte[] imageData = volunteersService.downloadImage(name);
        if (imageData != null && imageData.length > 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG); // Set the content type based on your image type
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } else {
            logger.warn("Image data not found or empty for volunteer: {}", name);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Or return a default image response
        }
    }

//======================   Get all Volunteers =======================================================================  
    @GetMapping("/get/volunteers")
    public ResponseEntity<List<VolunteersDto>> getAllVolunteers() {
        List<VolunteersDto> volunteers = volunteersService.getAllVolunteers();
        logger.info("Retrieved {} Volunteers from the database", volunteers.size());
        return new ResponseEntity<>(volunteers, HttpStatus.OK);
    }

//================================ Get  by Volunteers serial Number ================================================================
    @GetMapping("/get/{sl}")
    public ResponseEntity<Volunteers> getVolunteersById(@PathVariable Long sl) {
        try {
        	Volunteers volunteers = volunteersService.getVolunteersById(sl);
            return ResponseEntity.ok(volunteers);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    

 //===============================================get by Volunteers NameAndPhone================================================
    @GetMapping("/find/{name}/{phone}")
    public ResponseEntity<?> findVolunteerByNameAndPhone(@PathVariable String name, @PathVariable Long phone) {
        Optional<Volunteers> volunteer = volunteersService.findVolunteerByNameAndPhone(name, phone);
        if (volunteer.isPresent()) {
            return ResponseEntity.ok(volunteer.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Volunteer not found");
        }
    }
      
//================================= Update  by Volunteers Id ==============================================================
    
    @PutMapping(value = "/update/{sl}", consumes = "multipart/form-data")
    public ResponseEntity<String> updateVolunteers(
            @PathVariable Long sl,
            @RequestParam(value = "areaname", required = false) String areaname,
            @RequestParam(value = "phone", required = false) Long phone,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "image_upload", required = false) MultipartFile image_upload
    ) {
        Volunteers volunteers = new Volunteers();
        volunteers.setAreaname(areaname);
        volunteers.setPhone(phone);
        volunteers.setAddress(address);

        volunteersService.partialUpdateVolunteers(sl, volunteers, image_upload);

        return ResponseEntity.status(HttpStatus.OK).body("Volunteers updated successfully");
    }
//=====================================  Delete  by Volunteers Id ====================================================
    @DeleteMapping("/delete/{sl}")
    public ResponseEntity<Void> deleteVolunteers(@PathVariable Long sl) {
    	volunteersService.deleteVolunteers(sl);
        logger.info("Deleted company with ID: {}", sl);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	    
    //delete by phone number
    @DeleteMapping("/delete/phone/{phone}")
    public ResponseEntity<Void> deleteVolunteersByPhone(@PathVariable Long phone) {
    	volunteersService.deleteVolunteersByPhone(phone);
        logger.info("Deleted company with phone: {}", phone);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
//========================================= Count the total Volunteers =================================================
	    @GetMapping("/count/volunteers")
	    public long countVolunteers()
	    {
	    	return volunteersService.countVolunteers();
	    }
	
	 
}
