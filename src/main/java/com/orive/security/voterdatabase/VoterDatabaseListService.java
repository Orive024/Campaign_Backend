package com.orive.security.voterdatabase;

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




@Service
public class VoterDatabaseListService {

private static final Logger logger= LoggerFactory.getLogger(VoterDatabaseListService.class);
	
	@Autowired
	private VoterDatabaseListRepository voterRepository;
	
	@Autowired
	private ModelMapper  modelMapper;
	
	// Create
		 public VoterdatabaseDtoList createVoter(VoterdatabaseDtoList voterDto) {
			 VoterDatabaseList voter = convertToEntity(voterDto);
			 VoterDatabaseList savedVoter = voterRepository.save(voter);
		        logger.info("Created Voter with ID: {}", savedVoter.getSl());
		        return convertToDTO(savedVoter);
		    }

		 
		//upload excelsheet   
		    public void save(MultipartFile file)
			{
				try {
				List<VoterDatabaseList> voters=VoterDatabaseListExcelHelper.convertExcelToListOfvoters(file.getInputStream());
				this.voterRepository.saveAll(voters);
				
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		    
		    
			//get uplod excellsheet
			public List<VoterDatabaseList> getAllVoters()
			{
				return this.voterRepository.findAll();
				
			}
		 
		 
	    // Read
	    public List<VoterdatabaseDtoList> getAllVoter() {
	        List<VoterDatabaseList> voters = voterRepository.findAll();
	        logger.info("Retrieved {} Voter from the database", voters.size());
	        return voters.stream()
	                .map(this::convertToDTO)
	                .collect(Collectors.toList());
	    }
	    
	    //get by pincode
	    public List<VoterdatabaseDtoList> getVoterByPincode(String pincode) {
	        List<VoterDatabaseList> voters = voterRepository.findByPincode(pincode);
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
	    private VoterDatabaseList convertToEntity(VoterdatabaseDtoList voterDto )
	    {
	    	return modelMapper.map(voterDto, VoterDatabaseList.class);
	    }

	    // Helper method to convert Voter  to VoterDto
	    private VoterdatabaseDtoList convertToDTO(VoterDatabaseList voter ) 
	    {
	        return modelMapper.map(voter, VoterdatabaseDtoList.class);
	    } 

}
