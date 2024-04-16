package com.orive.security.donor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DonorService {

    private static final Logger logger = Logger.getLogger(DonorService.class.getName());

    @Autowired
    private DonorRepository donorRepository;

    public List<Donor> getAllDonors() {
        logger.log(Level.INFO, "Fetching all donors");
        return donorRepository.findAll();
    }

    public Donor getDonorById(Long donorId) {
        Optional<Donor> donorOptional = donorRepository.findById(donorId);
        if (donorOptional.isPresent()) {
            logger.log(Level.INFO, "Donor found with ID: " + donorId);
            return donorOptional.get();
        } else {
            logger.log(Level.WARNING, "Donor not found with ID: " + donorId);
            return null;
        }
    }

    public Donor createDonor(Donor donor) {
        Donor savedDonor = donorRepository.save(donor);
        logger.log(Level.INFO, "Donor created: " + savedDonor.toString());
        return savedDonor;
    }

    public Donor updateDonor(Donor donor) {
    	try {
            if (donor.getDonorId() != null) {
                Optional<Donor> existingOpportunityOptional = donorRepository.findById(donor.getDonorId());
                if (existingOpportunityOptional.isPresent()) {
                	Donor existingOpportunity = existingOpportunityOptional.get();
                    if (donor.getDonorname() != null) {
                        existingOpportunity.setDonorname(donor.getDonorname());
                    }
                    if (donor.getAddress() != null) {
                        existingOpportunity.setAddress(donor.getAddress());
                    }
                    if (donor.getAge() != null) {
                        existingOpportunity.setAge(donor.getAge());
                    }
                    if (donor.getEmail() != null) {
                        existingOpportunity.setEmail(donor.getEmail());
                    }
                    if (donor.getPhone() != null) {
                        existingOpportunity.setPhone(donor.getPhone());
                    }
                    if (donor.getProfession() != null) {
                        existingOpportunity.setProfession(donor.getProfession());
                    }
                    if (donor.getSex() != null) {
                        existingOpportunity.setSex(donor.getSex());
                    }
                    Donor updatedOpportunity = donorRepository.save(existingOpportunity);
                    logger.log(Level.INFO, "donor updated successfully: " + updatedOpportunity.toString());
                    return updatedOpportunity;
                } else {
                    logger.log(Level.WARNING, "donor not found with ID: " + donor.getDonorId());
                }
            } else {
                logger.log(Level.WARNING, "donor ID cannot be null.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update donor: " + donor.toString(), e);
        }
        return null; // Return null or throw an exception based on your error handling strategy
    }

    public void deleteDonor(Long donorId) {
        donorRepository.deleteById(donorId);
        logger.log(Level.INFO, "Donor deleted with ID: " + donorId);
    }
}

