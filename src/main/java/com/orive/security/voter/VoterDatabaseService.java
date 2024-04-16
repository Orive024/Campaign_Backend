package com.orive.security.voter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
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
public class VoterDatabaseService {

private static final Logger logger= LoggerFactory.getLogger(VoterDatabaseService.class);
	
	@Autowired
	private VoterDatabaseRepository voterDatabaseRepository;
	
	@Autowired
	private ModelMapper  modelMapper;
	

    //================================= Create VoterDatabase =======================================================================
    public String saveVoterDatabase(
    		String register_voter,
    		String name,
    		String areaname,
    		String email,
    		String contacted,
    		String address,
    		String blood_group,
    		String voter_id,
    		String voter_categories,
    		Long phone,
    		String gender,
    		LocalDate birthdate,
            MultipartFile image_upload) {

        try {
            // Create a new VoterDatabase object and save it
        	VoterDatabase voterDatabase = VoterDatabase.builder()
                    .register_voter(register_voter)
                    .name(name)
                    .areaname(areaname)
                    .email(email)
                    .contacted(contacted)
                    .address(address)
                    .blood_group(blood_group)
                    .voter_id(voter_id)
                    .voter_categories(voter_categories)
                    .phone(phone)
                    .gender(gender)
                    .birthdate(birthdate)
                    .image_upload(VoterImageUtils.compressImage(image_upload.getBytes()))
                    .build();

        	VoterDatabase savedVoterDatabase = voterDatabaseRepository.save(voterDatabase);

            if (savedVoterDatabase != null) {
                return "VoterDatabase saved successfully with ID: " + savedVoterDatabase.getVoter_id();
            } else {
                return "Error: Failed to save VoterDatabase";
            }

        } catch (IOException e) {
            // Handle the IOException appropriately (e.g., log it, return an error message)
            return "Error uploading file: " + e.getMessage();
        }
    }

    //============================== Download Image =======================================================
    public byte[] downloadImage(String name) {
        Optional<VoterDatabase> dbImageData = voterDatabaseRepository.findByVoterName(name);
        if (dbImageData.isPresent()) {
            byte[] compressedData = dbImageData.get().getImage_upload();
            if (compressedData != null && compressedData.length > 0) {
                return VoterImageUtils.decompressImage(compressedData);
            } else {
                logger.warn("Image data is empty or invalid for VoterDatabase: {}", name);
            }
        } else {
            logger.warn("Image data not found for VoterDatabase: {}", name);
        }
        return null; // or return a default image byte array
    }

  //============================== upload excelsheet  =======================================================  
    public void save(MultipartFile file)
	{
		try {
		List<VoterDatabase> voterDatabases=VoterExcelHelper.convertExcelToListOfvoters(file.getInputStream());
		this.voterDatabaseRepository.saveAll(voterDatabases);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
  //============================== get uplod excellsheet  =======================================================  
	public List<VoterDatabase> getAllVoterdatabase()
	{
		return this.voterDatabaseRepository.findAll();
		
	}
    
    
    
    //========================================== Read ====================================================================
    public List<VoterDatabaseDto> getAllVoterDatabase() {
        List<VoterDatabase> voterDatabases = voterDatabaseRepository.findAll();
        logger.info("Retrieved {} VoterDatabase from the database", voterDatabases.size());
        return voterDatabases.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //==================================== get VoterDatabase By VoterID =============================================================

    public List<VoterDatabaseDto> getVoterById(String voter_id) {
        List<VoterDatabase> voterDatabase = voterDatabaseRepository.findByVoterId(voter_id);
        if (voterDatabase.isEmpty()) {
            logger.warn("Voter with ID {} not found", voter_id);
            return Collections.emptyList();
        }

        return voterDatabase.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    //===============================================get by VoterDatabase NameAndPhone================================================
    
    public Optional<VoterDatabase> findVoterByNameAndPhone(String name, Long phone) {
        logger.info("Searching for Voter with name '{}' and phone '{}'", name, phone);
        Optional<VoterDatabase> voter = voterDatabaseRepository.findByNameAndPhone(name, phone);
        if (voter.isPresent()) {
            logger.info("VoterDatabase found: {}", voter.get());
        } else {
            logger.warn("VoterDatabase not found with name '{}' and phone '{}'", name, phone);
        }
        return voter;
    }
    
    
    //======================================= Update VoterDatabase by id ===========================================================
    public void partialUpdateVoterDatabase(String voter_id, VoterDatabase voterDatabase, MultipartFile image_upload) {
        List<VoterDatabase> existingVoterDatabaseOptional = voterDatabaseRepository.findByVoterId(voter_id);
        if (!existingVoterDatabaseOptional.isEmpty()) {
            VoterDatabase existingVoterDatabase = existingVoterDatabaseOptional.get(0);

            // Update fields conditionally only if they are not null in VoterDatabase
            if (voterDatabase.getAreaname() != null) {
                existingVoterDatabase.setAreaname(voterDatabase.getAreaname());
            }

            if (voterDatabase.getPhone() != null) {
                existingVoterDatabase.setPhone(voterDatabase.getPhone());
            }

            if (voterDatabase.getAddress() != null) {
                existingVoterDatabase.setAddress(voterDatabase.getAddress());
            }
            
            if (voterDatabase.getEmail() != null) {
                existingVoterDatabase.setEmail(voterDatabase.getEmail());
            }

            // Update image_upload only if a new file is provided
            if (image_upload != null && !image_upload.isEmpty()) {
                try {
                    byte[] newImageUploadData = VoterImageUtils.compressImage(image_upload.getBytes());
                    existingVoterDatabase.setImage_upload(newImageUploadData);
                } catch (IOException e) {
                    // Handle the IOException appropriately (e.g., log it, return an error message)
                    logger.error("Error updating ImageUpload: {}", e.getMessage());
                }
            }

            // Save the updated entity
            voterDatabaseRepository.save(existingVoterDatabase);
            logger.info("Updated VoterDatabase with ID: {}", existingVoterDatabase.getVoter_id());
        } else {
            logger.warn("VoterDatabase with ID {} not found for update", voter_id);
        }
    }



    //========================================== Delete =================================================================
    public void deleteVoters(String voter_id) {
    	voterDatabaseRepository.deleteByVoterId(voter_id);
        logger.info("Deleted VoterDatabase with ID: {}", voter_id);
    }

    //============================ count the total VoterDatabase =============================================================
    public long countVoterDatabase() {
        return voterDatabaseRepository.count();
    }

    //==========================  Helper method to convert VoterDatabaseDto to VoterDatabase ==================================
    private VoterDatabase convertToEntity(VoterDatabaseDto voterDatabaseDto) {
        return modelMapper.map(voterDatabaseDto, VoterDatabase.class);
    }

    //==========================  Helper method to convert VoterDatabase  to VoterDatabaseDto ==================================
    private VoterDatabaseDto convertToDTO(VoterDatabase voterDatabase) {
        return modelMapper.map(voterDatabase, VoterDatabaseDto.class);
    }
	    
}
