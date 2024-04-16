package com.orive.security.donor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orive.security.campaignAnalysis.Weakness;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DonationService {

    private static final Logger logger = Logger.getLogger(DonationService.class.getName());

    @Autowired
    private DonationRepository donationRepository;

    public List<Donation> getAllDonations() {
        logger.log(Level.INFO, "Fetching all donations");
        return donationRepository.findAll();
    }

    public Donation getDonationById(Long donationId) {
        Optional<Donation> donationOptional = donationRepository.findById(donationId);
        if (donationOptional.isPresent()) {
            logger.log(Level.INFO, "Donation found with ID: " + donationId);
            return donationOptional.get();
        } else {
            logger.log(Level.WARNING, "Donation not found with ID: " + donationId);
            return null;
        }
    }

    public Donation createDonation(Donation donation) {
        Donation savedDonation = donationRepository.save(donation);
        logger.log(Level.INFO, "Donation created: " + savedDonation.toString());
        return savedDonation;
    }

    public Donation updateDonation(Donation donation) {
    	try {
            if (donation.getDonationId() != null) {
                Optional<Donation> existingOpportunityOptional = donationRepository.findById(donation.getDonationId());
                if (existingOpportunityOptional.isPresent()) {
                	Donation existingOpportunity = existingOpportunityOptional.get();
                    if (donation.getDonorname() != null) {
                        existingOpportunity.setDonorname(donation.getDonorname());
                    }
                    if (donation.getAmount() != null) {
                        existingOpportunity.setAmount(donation.getAmount());
                    }
                    if (donation.getType() != null) {
                        existingOpportunity.setType(donation.getType());
                    }
                    if (donation.getDate() != null) {
                        existingOpportunity.setDate(donation.getDate());
                    }
                    Donation updatedOpportunity = donationRepository.save(existingOpportunity);
                    logger.log(Level.INFO, "donation updated successfully: " + updatedOpportunity.toString());
                    return updatedOpportunity;
                } else {
                    logger.log(Level.WARNING, "donation not found with ID: " + donation.getDonationId());
                }
            } else {
                logger.log(Level.WARNING, "donation ID cannot be null.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update donation: " + donation.toString(), e);
        }
        return null; // Return null or throw an exception based on your error handling strategy
    }

    public void deleteDonation(Long donationId) {
        donationRepository.deleteById(donationId);
        logger.log(Level.INFO, "Donation deleted with ID: " + donationId);
    }
    
    public BigDecimal getTotalCashAmount() {
        BigDecimal totalCashAmount = donationRepository.getTotalCashAmount();
        logger.log(Level.INFO, "Total cash amount: " + totalCashAmount);
        return totalCashAmount;
    }

    public BigDecimal getTotalChequeAmount() {
        BigDecimal totalChequeAmount = donationRepository.getTotalChequeAmount();
        logger.log(Level.INFO, "Total cheque amount: " + totalChequeAmount);
        return totalChequeAmount;
    }

    public BigDecimal getTotalOtherAmount() {
        BigDecimal totalOtherAmount = donationRepository.getTotalOtherAmount();
        logger.log(Level.INFO, "Total other amount: " + totalOtherAmount);
        return totalOtherAmount;
    }
    
    public BigDecimal getTotalAmountOfAllDonations() {
        return donationRepository.getTotalAmountOfAllDonations();
    }
}
