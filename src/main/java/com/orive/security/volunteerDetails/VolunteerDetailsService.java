package com.orive.security.volunteerDetails;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
public class VolunteerDetailsService {
	

		
	private static final Logger logger = LoggerFactory.getLogger(VolunteerDetailsService.class);
		
		@Autowired
		private VolunteerDetailsRepository volunteerDetailsRepository;
		
		@Autowired
		private ModelMapper modelMapper;

		// Create
		public VolunteerDetailsDto createsVolunteerDetails(VolunteerDetailsDto volunteerDetailsDto) {
		    String volunteername = volunteerDetailsDto.getVolunteername();
		    String emailaddress = volunteerDetailsDto.getEmailaddress();

		    // Check if entry already exists for the same Volunteername and Emailaddress
		    if (volunteerDetailsRepository.existsByVolunteernameAndEmailaddress(volunteername, emailaddress)) {
		        logger.warn("VolunteerDetails for Volunteername: {} and Emailaddress: {} already exists.", volunteername, emailaddress);
		        return null;
		    }

		    VolunteerDetails volunteerDetails = convertToEntity(volunteerDetailsDto);
		    VolunteerDetails savedVolunteerDetails = volunteerDetailsRepository.save(volunteerDetails);
		    logger.info("Created VolunteerDetails with ID: {}", savedVolunteerDetails.getVolunteerid());
		    return convertToDTO(savedVolunteerDetails);
		}
		
		
	    
	    //upload excelsheet   
	    public void save(MultipartFile file)
		{
			try {
			List<VolunteerDetails> volunteerDetails=VolunteerExcelHelper.convertExcelToListOfVolunteerDetails(file.getInputStream());
			this.volunteerDetailsRepository.saveAll(volunteerDetails);
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    
	    
		//get uplod excellsheet
		public List<VolunteerDetails> getAllVolunteerDetails()
		{
			return this.volunteerDetailsRepository.findAll();
			
		}

		
	    // Read
	    public List<VolunteerDetailsDto> getAllVolunteerDetail() {
	        List<VolunteerDetails> volunteerDetails = volunteerDetailsRepository.findAll();
	        logger.info("Retrieved {} VolunteerDetails from the database", volunteerDetails.size());
	        return volunteerDetails.stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }
	    
	    
	    //get VolunteerDetails by VolunteerId
	    public List<VolunteerDetailsDto> getVolunteerDetailsByVolunteerId(String volunteerid) {
	        List<VolunteerDetails> volunteer = volunteerDetailsRepository.findByVolunteerId(volunteerid);
	        if (volunteer.isEmpty()) {
	            logger.warn("VolunteerDetails with ID {} not found", volunteerid);
	            return Collections.emptyList();
	        }

	        return volunteer.stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    } 
	    
	    
	    //get VolunteerDetails by  Volunteername And Mobilenumber
	    public Optional<VolunteerDetailsDto> getVolunteerDetailsByNameAndMobileNumber(String volunteername, Long mobilenumber) {
	        Optional<VolunteerDetails> volunteer = volunteerDetailsRepository.findByVolunteernameAndMobilenumber(volunteername,mobilenumber);
	        if (volunteer.isPresent()) {
	            return Optional.of(convertToDTO(volunteer.get()));
	        } else {
	            logger.warn("VolunteerDetails with Volunteername And Mobilenumber {} not found", volunteername,mobilenumber);
	            return Optional.empty();
	        }
	    }
	      
	    
	    
	    // Update list by id  
	    public VolunteerDetailsDto updateVolunteerDetails(String volunteerid, VolunteerDetailsDto volunteerDetailsDto) {
	        List<VolunteerDetails> existingVolunteerDetails = volunteerDetailsRepository.findByVolunteerId(volunteerid);
	        if (!existingVolunteerDetails.isEmpty()) {
	            VolunteerDetails existingDetails = existingVolunteerDetails.get(0);
	            existingDetails.setMobilenumber(volunteerDetailsDto.getMobilenumber());
	            existingDetails.setAddress(volunteerDetailsDto.getAddress());
	            existingDetails.setEmailaddress(volunteerDetailsDto.getEmailaddress());
	            // Assuming modelMapper is properly configured and used elsewhere
	            modelMapper.map(volunteerDetailsDto, existingDetails);
	            VolunteerDetails updatedVolunteerDetails = volunteerDetailsRepository.save(existingDetails);
	            logger.info("Updated VolunteerDetails with ID: {}", updatedVolunteerDetails.getVolunteerid());
	            return convertToDTO(updatedVolunteerDetails);
	        } else {
	            logger.warn("VolunteerDetails with ID {} not found for update", volunteerid);
	            return null;
	        }
	    }

	    
	    
	    
	    // Update list by Volunteername And Mobilenumber
	    public VolunteerDetailsDto updateVolunteerDetailsByVolunteernameAndMobilenumber(String volunteername, Long mobilenumber, VolunteerDetailsDto volunteerDetailsDto) {
	        Optional<VolunteerDetails> existingCampaignDetails = volunteerDetailsRepository.findByVolunteernameAndMobilenumber(volunteername,mobilenumber);
	        if (existingCampaignDetails.isPresent()) {
	        	VolunteerDetails existingDetails = existingCampaignDetails.get();
	        	existingDetails.setMobilenumber(volunteerDetailsDto.getMobilenumber());
	            existingDetails.setAddress(volunteerDetailsDto.getAddress());
	            existingDetails.setEmailaddress(volunteerDetailsDto.getEmailaddress());
	            modelMapper.map(volunteerDetailsDto, existingDetails);
	            VolunteerDetails updatedVolunteerDetails = volunteerDetailsRepository.save(existingDetails);
	            logger.info("Updated VolunteerDetails with Volunteername And Mobilenumber: {}", updatedVolunteerDetails.getVolunteerid());
	            return convertToDTO(updatedVolunteerDetails);
	        } else {
	            logger.warn("VolunteerDetails with updatedVolunteerDetails {} not found for update",volunteername);
	            return null;
	        }
	    }
	    
	    
//	    // Delete
	    @Transactional
	    public void deleteVolunteerDetails(String volunteerid) {
	    	volunteerDetailsRepository.deleteByVolunteerId(volunteerid);
	        logger.info("Deleted VolunteerDetails with ID: {}", volunteerid);
	    }
	    
	  
	    //count the total VolunteerDetails
	    public long countVolunteerDetails()
		 {
			 return volunteerDetailsRepository.count();
		 }                   
	      
	    
		// Helper method to convert VolunteerDetailsDto to VolunteerDetails
	    private VolunteerDetails convertToEntity(VolunteerDetailsDto volunteerDetailsDto)
	    {
	    	return modelMapper.map(volunteerDetailsDto, VolunteerDetails.class);
	    }

	    // Helper method to convert VolunteerDetails entity to VolunteerDetailsDto
	    private VolunteerDetailsDto convertToDTO(VolunteerDetails volunteerDetails) {
	        return modelMapper.map(volunteerDetails, VolunteerDetailsDto.class);
	    } 

	}
