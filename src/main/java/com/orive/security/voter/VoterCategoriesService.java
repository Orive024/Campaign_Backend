package com.orive.security.voter;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class VoterCategoriesService {

private static final Logger logger = LoggerFactory.getLogger(VoterCategoriesService.class);
	
	@Autowired
	private VoterCategoriesRepository voterCategoriesRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	

	//create VoterCategories
	public VoterCategories createVoterCategories(VoterCategories voterCategories) {
		logger.info("Creating VoterCategories: {}", voterCategories);
		return voterCategoriesRepository.save(voterCategories);		
	}
	
	//Get All VoterCategories
	public List<VoterCategories> getAllVoterCategories(){
		logger.info("Fetching All VoterCategories");
		return voterCategoriesRepository.findAll();	
	}
	
	//Get VoterCategories By Id
	public Optional<VoterCategories> getVoterCategoriesById(Long sl) {
		logger.info("Fetching VoterCategories By Id: {}", sl);
		return voterCategoriesRepository.findById(sl);		
	}
	
	//Update VoterCategories By Id
	public VoterCategories updateVoterCategoriesById(Long sl, VoterCategories updatedVoterCategories) {
		logger.info("Updating VoterCategories with ID {}: {}", sl, updatedVoterCategories);
		Optional<VoterCategories> exsistingVoterCategories = voterCategoriesRepository.findById(sl);
		if(exsistingVoterCategories.isPresent()) {
			VoterCategories voterCategories = exsistingVoterCategories.get();
			voterCategories.setCategories(updatedVoterCategories.getCategories());
			voterCategories.setDescription(updatedVoterCategories.getDescription());
			return voterCategoriesRepository.save(voterCategories);	
		}else {
			logger.error("VoterCategories not found with ID: {}", sl);
			return updatedVoterCategories;
		}
	}
	
	//Count the total VoterCategories
	 public long countVoterCategories() {
	        return voterCategoriesRepository.count();
	    }
	
	//Delete VoterCategories By ID
	public void deleteVoterCategories(Long sl) {
		logger.info("Deleting VoterCategories with ID: {}", sl);
		voterCategoriesRepository.deleteById(sl);;
			
	}
}
