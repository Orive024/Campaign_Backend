package com.orive.security.campaignDetails;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class CampaignDetailsService {

	
private static final Logger logger = LoggerFactory.getLogger(CampaignDetailsService.class);
	
	@Autowired
	private CampaignDetailsRepository campaignDetailsRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	// Create
	public CampaignDetailsDto createsCampaignDetails(CampaignDetailsDto campaignDetailsDto) {
//		String campaignName = campaignDetailsDto.getCampaignname();
//		String campaignDate = campaignDetailsDto.getCampaigndate();
//
//	    // Check if entry already exists for the same campaignName and campaignDate
//	    if (campaignDetailsRepository.existsByCampaignnameAndCampaigndate(campaignName, campaignDate)) {
//	        logger.warn("CampaignDetails for CampaignName: {} and CampaignDate: {} already exists.", campaignName, campaignDate);
//	        return null;
//	    }

	    CampaignDetails campaignDetails = convertToEntity(campaignDetailsDto);
	    CampaignDetails savedCampaignDetails = campaignDetailsRepository.save(campaignDetails);
	    logger.info("Created CampaignDetails with ID: {}", savedCampaignDetails.getCampaigndetailsid());
	    return convertToDTO(savedCampaignDetails);
	}

	
	
//	
//	public String saveAttendanceEntity(
//			String employeeName,
//			LocalTime clockIn,
//			LocalTime clockOut,
//			Long late,
//			Long earlyLeaving,
//			Long overTime,
//			Long totalWork,
//			Long totalRest,
//			LocalDate date,
//			MultipartFile file) {
//		
//
//        try {
//        	byte[] compressedFile = ExcelHelper.compressExcel(file.getBytes()); // Use your Excel compression logic
//
//            AttendanceEntity docData = attendanceRepository.save(AttendanceEntity.builder()
//                    .employeeName(employeeName)
//                    .clockIn(clockIn)
//                    .clockOut(clockOut)
//                    .late(late)
//                    .earlyLeaving(earlyLeaving)
//                    .overTime(overTime)
//                    .totalWork(totalWork)
//                    .totalRest(totalRest)
//                    .date(date)
//                    .uploadDoc(compressedFile)
//                    .build());
//
//            if (docData != null) {
//                return "File uploaded successfully: " + file.getOriginalFilename();
//            }
//        } catch (IOException e) {
//            // Handle the IOException appropriately (e.g., log it, return an error message)
//            return "Error uploading file: " + e.getMessage();
//        }
//
//        return null;
//    }
//	
//	//Download excel file
//	public byte[] downloadExcel(Long attendanceId) {
//	    Optional<AttendanceEntity> dbDocData = attendanceRepository.findById(attendanceId);
//
//	    if (dbDocData.isPresent()) {
//	        return dbDocData.get().getUploadDoc();
//	    } else {
//	        // Handle the case where the attendance data is not found
//	        return null;
//	    }
//	}
    
    
    //upload excelsheet   
    public void save(MultipartFile file)
	{
		try {
		List<CampaignDetails> campaignDetails=ExcelHelper.convertExcelToListOfCampaignDetails(file.getInputStream());
		this.campaignDetailsRepository.saveAll(campaignDetails);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    
	//get uplod excellsheet
	public List<CampaignDetails> getAllCampaignDetails()
	{
		return this.campaignDetailsRepository.findAll();
		
	}

    // Read
    public List<CampaignDetailsDto> getAllCampaignDetail() {
        List<CampaignDetails> campaignDetails = campaignDetailsRepository.findAll();
        logger.info("Retrieved {} CampaignDetails from the database", campaignDetails.size());
        return campaignDetails.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    //get by CampaigndetailsId
    public Optional<CampaignDetailsDto> getCampaigndetailsById(Long campaigndetailsid) {
        Optional<CampaignDetails> campaignDetails = campaignDetailsRepository.findById(campaigndetailsid);
        if (campaignDetails.isPresent()) {
            return Optional.of(convertToDTO(campaignDetails.get()));
        } else {
            logger.warn("CampaignDetails with ID {} not found", campaigndetailsid);
            return Optional.empty();
        }
    }
    
    
 // Update list by id
    public CampaignDetailsDto updateCampaigndetails(Long campaigndetailsid, CampaignDetailsDto campaignDetailsDto) {
        Optional<CampaignDetails> existingCampaignDetails = campaignDetailsRepository.findById(campaigndetailsid);
        if (existingCampaignDetails.isPresent()) {
        	CampaignDetails existingDetails = existingCampaignDetails.get();
        	existingDetails.setCampaignbudget(campaignDetailsDto.getCampaignbudget());
        	existingDetails.setVolunteersassigned(campaignDetailsDto.getVolunteersassigned());
        	existingDetails.setCampaignmaterials(campaignDetailsDto.getCampaignmaterials());
        	modelMapper.map(campaignDetailsDto, existingCampaignDetails);
        	CampaignDetails updatedCampaignDetails= campaignDetailsRepository.save(existingDetails);
            logger.info("Updated CampaignDetails with ID: {}", updatedCampaignDetails.getCampaigndetailsid());
            return convertToDTO(updatedCampaignDetails);
        } else {
            logger.warn("CampaignDetails with ID {} not found for update", campaigndetailsid);
            return null;
        }
    }
    
    
    
    // Update list by campaignName And campaignDate
    public CampaignDetailsDto updateCampaignDetailsByCampaignNameAndDate(String campaignname, String campaigndate, CampaignDetailsDto campaignDetailsDto) {
        Optional<CampaignDetails> existingCampaignDetails = campaignDetailsRepository.findByCampaignNameAndDate(campaignname,campaigndate);
        if (existingCampaignDetails.isPresent()) {
        	CampaignDetails existingDetails = existingCampaignDetails.get();
        	existingDetails.setCampaignbudget(campaignDetailsDto.getCampaignbudget());
        	existingDetails.setVolunteersassigned(campaignDetailsDto.getVolunteersassigned());
        	existingDetails.setCampaignmaterials(campaignDetailsDto.getCampaignmaterials());
        	modelMapper.map(campaignDetailsDto, existingCampaignDetails);
        	CampaignDetails updatedCampaignDetails= campaignDetailsRepository.save(existingDetails);
            logger.info("Updated CampaignDetails with campaignName and campaignDate: {}", updatedCampaignDetails.getCampaigndetailsid());
            return convertToDTO(updatedCampaignDetails);
        } else {
            logger.warn("CampaignDetails with campaignName and campaignDate {} not found for update",campaignname);
            return null;
        }
    }
    
    
    // Delete
    public void deleteCampaignDetails(Long campaigndetailsid) {
    	campaignDetailsRepository.deleteById(campaigndetailsid);
        logger.info("Deleted CampaignDetails with ID: {}", campaigndetailsid);
    }

    //count the total CampaignDetails
    public long countCampaignDetails()
	 {
		 return campaignDetailsRepository.count();
	 }                   
      
    
	// Helper method to convert CampaignDetailsDto to CampaignDetails
    private CampaignDetails convertToEntity(CampaignDetailsDto campaignDetailsDto)
    {
    	return modelMapper.map(campaignDetailsDto, CampaignDetails.class);
    }

    // Helper method to convert CampaignDetails entity to CampaignDetailsDto
    private CampaignDetailsDto convertToDTO(CampaignDetails campaignDetails) {
        return modelMapper.map(campaignDetails, CampaignDetailsDto.class);
    } 

}
