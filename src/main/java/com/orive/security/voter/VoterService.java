package com.orive.security.voter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.orive.security.volunteerDetails.VolunteerDetails;
import com.orive.security.volunteerDetails.VolunteerExcelHelper;


@Service
public class VoterService {

private static final Logger logger= LoggerFactory.getLogger(VoterService.class);
	
	@Autowired
	private VoterRepository voterRepository;
	
	@Autowired
	private ModelMapper  modelMapper;
	
	// Create
		 public VoterDto createVoter(VoterDto voterDto) {
			 Voter voter = convertToEntity(voterDto);
			 Voter savedVoter = voterRepository.save(voter);
		        logger.info("Created Voter with ID: {}", savedVoter.getSl());
		        return convertToDTO(savedVoter);
		    }

		 
		//upload excelsheet   
		    public void save(MultipartFile file)
			{
				try {
				List<Voter> voters=VoterExcelHelper.convertExcelToListOfvoters(file.getInputStream());
				this.voterRepository.saveAll(voters);
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		    
		    
			//get uplod excellsheet
			public List<Voter> getAllVoters()
			{
				return this.voterRepository.findAll();
				
			}
		 
		 
	    // Read
	    public List<VoterDto> getAllVoter() {
	        List<Voter> voters = voterRepository.findAll();
	        logger.info("Retrieved {} Voter from the database", voters.size());
	        return voters.stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }
	    
	    //get by pincode
	    public List<VoterDto> getVoterByPincode(String pincode) {
	        List<Voter> voters = voterRepository.findByPincode(pincode);
	        if (voters.isEmpty()) {
	            logger.warn("Voter with ID {} not found", pincode);
	            return Collections.emptyList();
	        }

	        return voters.stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    } 
	    
	    
	    
	    
//	 // Update list by id
//	    public VoterDto updateVoter(Long sl, VoterDto voterDto) {
//	        Optional<Voter> existingVoterOPtional = voterRepository.findById(sl);
//	        if (existingVoterOPtional.isPresent()) {
//	        	Voter existingVoter = existingVoterOPtional.get();
//	        	modelMapper.map(voterDto, existingVoterOPtional);
//	        	Voter updatedVoter = voterRepository.save(existingVoter);
//	            logger.info("Updated Voter with ID: {}", updatedVoter.getSl());
//	            return convertToDTO(updatedVoter);
//	        } else {
//	            logger.warn("Voter with ID {} not found for update", sl);
//	            return null;
//	        }
//	    }
//	    
	    
	    // Delete
	    public void deleteVoter(Long sl) {
	    	voterRepository.deleteById(sl);
	        logger.info("Deleted Voter with ID: {}", sl);
	    }

	    //count the total Voter
	    public long countVoter()
		 {
			 return voterRepository.count();
		 }
	    
		// Helper method to convert VoterDto to Voter
	    private Voter convertToEntity(VoterDto voterDto )
	    {
	    	return modelMapper.map(voterDto, Voter.class);
	    }

	    // Helper method to convert Voter  to VoterDto
	    private VoterDto convertToDTO(Voter voter ) 
	    {
	        return modelMapper.map(voter, VoterDto.class);
	    } 

}
