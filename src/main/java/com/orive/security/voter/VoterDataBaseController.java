package com.orive.security.voter;

import java.time.LocalDate;
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
@RequestMapping(value = "voterdatabase")
@CrossOrigin(origins = "*")
public class VoterDataBaseController {

	
	private static final Logger logger = LoggerFactory.getLogger(VoterDataBaseController.class);

    @Autowired
    private VoterDatabaseService voterDatabaseService;
    
	    
	  //=============================== Create a new VoterDataBase ==============================================================
		
	    @PostMapping(value = "/create/voterdatabase" , consumes = "multipart/form-data")  
	    public ResponseEntity<?> saveVolunteers(
	  		  @RequestParam(value = "register_voter", required = false)  String register_voter,
	  		  @RequestParam(value = "name", required = false)String name,
	  		  @RequestParam(value = "areaname", required = false) String areaname,
	  		  @RequestParam(value = "email", required = false) String email,
	  		  @RequestParam(value = "contacted", required = false) String contacted,
	  		  @RequestParam(value = "address", required = false) String address,
	  		  @RequestParam(value = "blood_group", required = false) String blood_group,
	  		  @RequestParam(value = "voter_id", required = false) String voter_id,
	  		  @RequestParam(value = "voter_categories", required = false)String voter_categories,
	  		  @RequestParam(value = "phone", required = false)Long phone,
	  		  @RequestParam(value = "gender", required = false)String gender,
	  		  @RequestParam(value = "birthdate", required = false)LocalDate birthdate,
	  		  @RequestParam(value = "image_upload", required = false) MultipartFile image_upload
	           ) {
	  	  String result = voterDatabaseService.saveVoterDatabase(register_voter, name, areaname, email, contacted, address, blood_group, voter_id, voter_categories, phone, gender, birthdate, image_upload);
	  			  
	    
	  	  if (result != null && result.startsWith("Error")) {
	            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	        } else if (result != null) {
	            return new ResponseEntity<>(result, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("Failed to save VoterDataBase", HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	  
	  
	  
	//===================== Get VoterDataBase logo by name ==================================================================
	    @GetMapping("/{name}")
	    public ResponseEntity<byte[]> downloadImage(@PathVariable String name) {
	        byte[] imageData = voterDatabaseService.downloadImage(name);
	        if (imageData != null && imageData.length > 0) {
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.IMAGE_PNG); // Set the content type based on your image type
	            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
	        } else {
	            logger.warn("Image data not found or empty for VoterDataBase: {}", name);
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Or return a default image response
	        }
	    }

	  //============================== upload excelsheet =======================================================     
	    @PostMapping("/excelfile/upload")
		public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file)
		{
			if(VoterExcelHelper.chechExcelFormat(file))
			{
				//true
				this.voterDatabaseService.save(file);
				return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to database"));
			}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please upload excel file ");
		}
		

	  //============================== get all excelsheet  =======================================================  
		@GetMapping("/get/excelfile")
		public List<VoterDatabase> getAllVoterDatabase()
		{
			return this.voterDatabaseService.getAllVoterdatabase();
		}
	    
	//======================   Get all VoterDataBase =======================================================================  
	    @GetMapping("/get/voterdatabase")
	    public ResponseEntity<List<VoterDatabaseDto>> getAllVoterDataBase() {
	        List<VoterDatabaseDto> voterDatabase = voterDatabaseService.getAllVoterDatabase();
	        logger.info("Retrieved {} Volunteers from the database", voterDatabase.size());
	        return new ResponseEntity<>(voterDatabase, HttpStatus.OK);
	    }

	//================================ Get  by VoterDataBase By Id ================================================================
	    
		  @GetMapping("/getid/{voter_id}")
		    public ResponseEntity<List<VoterDatabaseDto>>  getVotersById(@PathVariable("voter_id") String voter_id) {
		        List<VoterDatabaseDto> voters = voterDatabaseService.getVoterById(voter_id);

		        if (voters.isEmpty()) {
		            return ResponseEntity.notFound().build();
		        } else {
		            return ResponseEntity.ok(voters);
		        }
		    }

	 //===============================================get by VoterDataBase NameAndPhone================================================
	    @GetMapping("/find/{name}/{phone}")
	    public ResponseEntity<?> findVoterByNameAndPhone(@PathVariable String name, @PathVariable Long phone) {
	        Optional<VoterDatabase> voterDataBase = voterDatabaseService.findVoterByNameAndPhone(name, phone);
	        if (voterDataBase.isPresent()) {
	            return ResponseEntity.ok(voterDataBase.get());
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("VoterDataBase not found");
	        }
	    }
	      
	//================================= Update  by VoterDataBase Id ==============================================================
	    
	    @PutMapping(value = "/update/{voter_id}", consumes = "multipart/form-data")
	    public ResponseEntity<String> updateVoterDataBase(
	            @PathVariable String voter_id,
	            @RequestParam(value = "areaname", required = false) String areaname,
	            @RequestParam(value = "phone", required = false) Long phone,
	            @RequestParam(value = "address", required = false) String address,
	            @RequestParam(value = "email", required = false) String email,
	            @RequestParam(value = "image_upload", required = false) MultipartFile image_upload
	    ) {
	    	VoterDatabase voterDatabase = new VoterDatabase();
	    	voterDatabase.setAreaname(areaname);
	    	voterDatabase.setPhone(phone);
	        voterDatabase.setAddress(address);
	        voterDatabase.setEmail(email);

	        voterDatabaseService.partialUpdateVoterDatabase(voter_id, voterDatabase, image_upload);

	        return ResponseEntity.status(HttpStatus.OK).body("VoterDataBase updated successfully");
	    }
	//=====================================  Delete  by VoterDataBase Id ====================================================
	    @DeleteMapping("/delete/{voter_id}")
	    public ResponseEntity<Void> deleteVoterDataBase(@PathVariable String voter_id) {
	    	voterDatabaseService.deleteVoters(voter_id);
	        logger.info("Deleted VoterDataBase with ID: {}", voter_id);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
		    
	//========================================= Count the total VoterDataBase =================================================
		    @GetMapping("/count/voterdatabase")
		    public long countVoterDataBase()
		    {
		    	return voterDatabaseService.countVoterDatabase();
		    }

}
