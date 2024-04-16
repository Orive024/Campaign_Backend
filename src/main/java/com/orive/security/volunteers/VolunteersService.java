package com.orive.security.volunteers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.orive.security.volunteers.ImageUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VolunteersService {

    private static final Logger logger = LoggerFactory.getLogger(VolunteersService.class);

    @Autowired
    private VolunteersRepository volunteersRepository;

    @Autowired
    private ModelMapper modelMapper;

    //================================= Create Volunteers =======================================================================
    public String saveVolunteers(
            String name,
            String gender,
            String email,
            String address,
            String areaname,
            Long phone,
            String password,
            String profile,
            MultipartFile image_upload) {

        try {
            // Create a new Volunteers object and save it
        	Volunteers volunteers = Volunteers.builder()
                    .name(name)
                    .gender(gender)
                    .email(email)
                    .address(address)
                    .areaname(areaname)
                    .phone(phone)
                    .password(password)
                    .profile(profile)
                    .image_upload(ImageUtils.compressImage(image_upload.getBytes()))
                    .build();

        	Volunteers savedVolunteers = volunteersRepository.save(volunteers);

            if (savedVolunteers != null) {
                return "Volunteers saved successfully with ID: " + savedVolunteers.getSl();
            } else {
                return "Error: Failed to save Volunteers";
            }

        } catch (IOException e) {
            // Handle the IOException appropriately (e.g., log it, return an error message)
            return "Error uploading file: " + e.getMessage();
        }
    }

    //============================== Download Image ======================================================================
    
    public byte[] downloadImage(String name) {
        Optional<Volunteers> dbImageData = volunteersRepository.findByVolunteerName(name);
        if (dbImageData.isPresent()) {
            byte[] compressedData = dbImageData.get().getImage_upload();
            if (compressedData != null && compressedData.length > 0) {
                return ImageUtils.decompressImage(compressedData);
            } else {
                logger.warn("Image data is empty or invalid for volunteer: {}", name);
            }
        } else {
            logger.warn("Image data not found for volunteer: {}", name);
        }
        return null; // or return a default image byte array
    }

    //========================================== Read ====================================================================
    public List<VolunteersDto> getAllVolunteers() {
        List<Volunteers> volunteers = volunteersRepository.findAll();
        logger.info("Retrieved {} Volunteers from the database", volunteers.size());
        return volunteers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    //==================================== get Volunteers By Serial Number ===============================================================
    public Volunteers getVolunteersById(Long sl) {
    	Volunteers volunteers = volunteersRepository.findById(sl)
                .orElseThrow(() -> {
                    logger.warn("Volunteers with Serial Number {} not found", sl);
                    return new ResourceNotFoundException("Volunteers with Serial Number " + sl + " not found");
                });
        return volunteers;
    }
   
    //===============================================get by Volunteers NameAndPhone================================================
    
    public Optional<Volunteers> findVolunteerByNameAndPhone(String name, Long phone) {
        logger.info("Searching for volunteer with name '{}' and phone '{}'", name, phone);
        Optional<Volunteers> volunteer = volunteersRepository.findByNameAndPhone(name, phone);
        if (volunteer.isPresent()) {
            logger.info("Volunteer found: {}", volunteer.get());
        } else {
            logger.warn("Volunteer not found with name '{}' and phone '{}'", name, phone);
        }
        return volunteer;
    }
    
    
    //======================================= Update Volunteers by id ===========================================================
    public void partialUpdateVolunteers(Long sl, Volunteers volunteers, MultipartFile image_upload) {
        Optional<Volunteers> existingVolunteersOptional = volunteersRepository.findById(sl);
        if (existingVolunteersOptional.isPresent()) {
            Volunteers existingVolunteers = existingVolunteersOptional.get();

            // Update fields conditionally only if they are not null in Volunteers
            if (volunteers.getAreaname() != null) {
                existingVolunteers.setAreaname(volunteers.getAreaname());
            }

            if (volunteers.getPhone() != null) {
                existingVolunteers.setPhone(volunteers.getPhone());
            }

            if (volunteers.getAddress() != null) {
                existingVolunteers.setAddress(volunteers.getAddress());
            }

            // Update image_upload only if a new file is provided
            if (image_upload != null && !image_upload.isEmpty()) {
                try {
                    byte[] newImageUploadData = ImageUtils.compressImage(image_upload.getBytes());
                    existingVolunteers.setImage_upload(newImageUploadData);
                } catch (IOException e) {
                    // Handle the IOException appropriately (e.g., log it, return an error message)
                    logger.error("Error updating Image_upload: {}", e.getMessage());
                }
            }

            // Save the updated entity
            volunteersRepository.save(existingVolunteers);
            logger.info("Updated Volunteers with ID: {}", existingVolunteers.getSl());
        } else {
            logger.warn("Volunteers with ID {} not found for update", sl);
        }
    }


    //========================================== Delete =================================================================
    public void deleteVolunteers(Long sl) {
    	volunteersRepository.deleteById(sl);
        logger.info("Deleted Volunteers with ID: {}", sl);
    }
    
    //delete by phone number
    public void deleteVolunteersByPhone(Long phone) {
    	volunteersRepository.deleteByPhone(phone);
        logger.info("Deleted Volunteers with phone: {}", phone);
    }

    //============================ count the total Volunteers =============================================================
    public long countVolunteers() {
        return volunteersRepository.count();
    }

    //==========================  Helper method to convert VolunteersDto to Volunteers ==================================
    private Volunteers convertToEntity(VolunteersDto volunteersDto) {
        return modelMapper.map(volunteersDto, Volunteers.class);
    }

    //==========================  Helper method to convert Volunteers  to VolunteersDto ==================================
    private VolunteersDto convertToDTO(Volunteers volunteers) {
        return modelMapper.map(volunteers, VolunteersDto.class);
    }
}
