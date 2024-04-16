package com.orive.security.areas;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreasService {
	
	private static final Logger logger = LoggerFactory.getLogger(AreasService.class);
	
	@Autowired
	private AreasRepository areasRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	

	//create Areas
	public Areas createAreas(Areas areas) {
		logger.info("Creating Areas: {}", areas);
		return areasRepository.save(areas);		
	}
	
	//Get All Areas
	public List<Areas> getAllAreas(){
		logger.info("Fetching All Areas");
		return areasRepository.findAll();	
	}
	
	//Get Areas By Id
	public Optional<Areas> getAreasById(Long areasId) {
		logger.info("Fetching Areas By Id: {}", areasId);
		return areasRepository.findById(areasId);		
	}
	
	//Update Araes By Id
	public Areas updateAreasById(Long areasId, Areas updatedArea) {
		logger.info("Updating area with ID {}: {}", areasId, updatedArea);
		Optional<Areas> exsistingAreas = areasRepository.findById(areasId);
		if(exsistingAreas.isPresent()) {
			Areas areas = exsistingAreas.get();
			areas.setAreaname(updatedArea.getAreaname());
			areas.setDescription(updatedArea.getDescription());
			return areasRepository.save(areas);	
		}else {
			logger.error("Area not found with ID: {}", areasId);
			return updatedArea;
		}
	}
	
	//Count the total Areas
	 public long countAreas() {
	        return areasRepository.count();
	    }
	
	//Delete Areas By ID
	public void deleteAreas(Long areasId) {
		logger.info("Deleting area with ID: {}", areasId);
		areasRepository.deleteById(areasId);;
			
	}
	
}
