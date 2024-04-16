package com.orive.security.campaignDetails;


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

import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequestMapping(value = "campaigndetails")
@CrossOrigin(origins = "*")
@Tag(name = "CampaignDetails")
public class CampaignDetailsController {
	
	
private static final Logger logger = LoggerFactory.getLogger(CampaignDetailsController.class);
	
	@Autowired
	private CampaignDetailsService campaignDetailsService;
	
	 @PostMapping("/create")
	    public ResponseEntity<CampaignDetailsDto> createCampaignDetails(@RequestBody CampaignDetailsDto campaignDetailsDto) {
	        logger.info("Received request to create CampaignDetails for CampaignName: {} and CampaignDate: {}", campaignDetailsDto.getCampaignname(), campaignDetailsDto.getCampaigndate());

	        CampaignDetailsDto createdCampaignDetails = campaignDetailsService.createsCampaignDetails(campaignDetailsDto);
	        if (createdCampaignDetails != null) {
	            logger.info("CampaignDetails created successfully with ID: {}", createdCampaignDetails.getCampaigndetailsid());
	            return new ResponseEntity<>(createdCampaignDetails, HttpStatus.CREATED);
	        } else {
	            logger.warn("CampaignDetails already exists for CampaignName: {} and CampaignDate: {}", campaignDetailsDto.getCampaignname(), campaignDetailsDto.getCampaigndate());
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	        }
	    }
	
	
//	 @PostMapping("/upload/attendance")
 // @PreAuthorize("hasRole('client_HR')")
//	    public ResponseEntity<String> uploadExcel(
//	            @RequestParam String employeeName,
//	            @RequestParam LocalTime clockIn,
//	            @RequestParam LocalTime clockOut,
//	            @RequestParam Long late,
//	            @RequestParam Long earlyLeaving,
//	            @RequestParam Long overTime,
//	            @RequestParam Long totalWork,
//	            @RequestParam Long totalRest,
//	            @RequestParam LocalDate date,
//	            @RequestParam("uploadDoc") MultipartFile file) {
//	        try {
//	            String result = attendanceService.saveAttendanceEntity(
//	                    employeeName, clockIn, clockOut, late, earlyLeaving, overTime, totalWork, totalRest, date, file);
//	            return new ResponseEntity<>(result, HttpStatus.OK);
//	        } catch (Exception e) {
//	            return new ResponseEntity<>("Error uploading Excel file: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	    }
//
//	    @GetMapping("/download/excel/{attendanceId}")
 // @PreAuthorize("hasRole('client_HR')")
//	    public ResponseEntity<byte[]> downloadExcel(@RequestParam Long attendanceId) {
//	        byte[] excelData = attendanceService.downloadExcel(attendanceId);
//
//	        if (excelData != null) {
//	            HttpHeaders headers = new HttpHeaders();
//	            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//	            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(attendanceId + "_attendance.xlsx").build());
//	            return new ResponseEntity<>(excelData, headers, HttpStatus.OK);
//	        } else {
//	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	        }
//	    }
//	
//	
    
    
    
//upload excelsheet   
    @PostMapping("/excelfile/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file)
	{
		if(ExcelHelper.chechExcelFormat(file))
		{
			//true
			this.campaignDetailsService.save(file);
			return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to database"));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please upload excel file ");
	}
	

    //get all excelsheet
	@GetMapping("/get/excelfile")
	public List<CampaignDetails> getAllCampaignDetails()
	{
		return this.campaignDetailsService.getAllCampaignDetails();
	}

    

    // Get all CampaignDetails   
    @GetMapping("/get/campaigndetails")
    public ResponseEntity<List<CampaignDetailsDto>> getAllCampaignDetail() {
        List<CampaignDetailsDto> campaignDetails = campaignDetailsService.getAllCampaignDetail();
        logger.info("Retrieved {} CampaignDetails from the database", campaignDetails.size());
        return new ResponseEntity<>(campaignDetails, HttpStatus.OK);
    }

    // Get CampaignDetails by ID
    @GetMapping("/get/{campaigndetailsid}")
    public ResponseEntity<CampaignDetailsDto> getCampaignDetailsById(@PathVariable Long campaigndetailsid) {
        Optional<CampaignDetailsDto> campaignDetails = campaignDetailsService.getCampaigndetailsById(campaigndetailsid);
        if (campaignDetails.isPresent()) {
            logger.info("Retrieved CampaignDetails with ID: {}", campaigndetailsid);
            return new ResponseEntity<>(campaignDetails.get(), HttpStatus.OK);
        } else {
            logger.warn("CampaignDetails with ID {} not found", campaigndetailsid);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    

    // Update CampaignDetails by ID
    @PutMapping("/update/{campaigndetailsid}")
    public ResponseEntity<CampaignDetailsDto> updateCampaignDetails(@PathVariable Long campaigndetailsid, @RequestBody CampaignDetailsDto updatedCampaignDetailsDto) {
    	CampaignDetailsDto updatedCampaignDetails = campaignDetailsService.updateCampaigndetails(campaigndetailsid, updatedCampaignDetailsDto);
        if (updatedCampaignDetails != null) {
            logger.info("Updated CampaignDetails with ID: {}", campaigndetailsid);
            return new ResponseEntity<>(updatedCampaignDetails, HttpStatus.OK);
        } else {
            logger.warn("CampaignDetails with ID {} not found for update", campaigndetailsid);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    

    // Update list by campaignName And campaignDate
    @PutMapping("/update/{campaignname}/{campaigndate}")
    public ResponseEntity<CampaignDetailsDto> updateCampaignDetailsByCampaignNameAndDate(@PathVariable String campaignname, @PathVariable String campaigndate, @RequestBody CampaignDetailsDto updatedCampaignDetailsDto) {
    	CampaignDetailsDto updatedCampaignDetails = campaignDetailsService.updateCampaignDetailsByCampaignNameAndDate(campaignname, campaigndate, updatedCampaignDetailsDto);
        if (updatedCampaignDetails != null) {
            logger.info("Updated CampaignDetails with CampaignName and CampaignDate: {}", campaignname,campaigndate);
            return new ResponseEntity<>(updatedCampaignDetails, HttpStatus.OK);
        } else {
            logger.warn("CampaignDetails with CampaignName and CampaignDate {} not found for update", campaignname,campaigndate);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
   
    
    
    // Delete CampaignDetails by ID
    @DeleteMapping("/delete/{campaigndetailsid}")
    public ResponseEntity<Void> deleteCampaignDetails(@PathVariable Long campaigndetailsid) {
    	campaignDetailsService.deleteCampaignDetails(campaigndetailsid);
        logger.info("Deleted CampaignDetails with ID: {}", campaigndetailsid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	    
    //count the total CampaignDetails
	    @GetMapping("/count/campaigndetails")
	    public long countCampaignDetails()
	    {
	    	return campaignDetailsService.countCampaignDetails();
	    }
}
