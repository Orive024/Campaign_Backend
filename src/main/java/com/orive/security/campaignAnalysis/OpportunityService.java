package com.orive.security.campaignAnalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class OpportunityService {

    private static final Logger logger = Logger.getLogger(OpportunityService.class.getName());

    @Autowired
    private OpportunityRepository opportunityRepository;

    public List<Opportunity> getAllOpportunities() {
        logger.log(Level.INFO, "Fetching all opportunities");
        return opportunityRepository.findAll();
    }

    public Opportunity getOpportunityById(Long opportunityId) {
        Optional<Opportunity> opportunityOptional = opportunityRepository.findById(opportunityId);
        if (opportunityOptional.isPresent()) {
            logger.log(Level.INFO, "Opportunity found with ID: " + opportunityId);
            return opportunityOptional.get();
        } else {
            logger.log(Level.WARNING, "Opportunity not found with ID: " + opportunityId);
            return null;
        }
    }

    public Opportunity createOpportunity(Opportunity opportunity) {
        Opportunity savedOpportunity = opportunityRepository.save(opportunity);
        logger.log(Level.INFO, "Opportunity created: " + savedOpportunity.toString());
        return savedOpportunity;
    }

    public Opportunity updateOpportunity(Opportunity opportunity) {
        try {
            if (opportunity.getOpportunityId() != null) {
                Optional<Opportunity> existingOpportunityOptional = opportunityRepository.findById(opportunity.getOpportunityId());
                if (existingOpportunityOptional.isPresent()) {
                    Opportunity existingOpportunity = existingOpportunityOptional.get();
                    if (opportunity.getOpportunity() != null) {
                        existingOpportunity.setOpportunity(opportunity.getOpportunity());
                    }
                    if (opportunity.getNote() != null) {
                        existingOpportunity.setNote(opportunity.getNote());
                    }
                    Opportunity updatedOpportunity = opportunityRepository.save(existingOpportunity);
                    logger.log(Level.INFO, "Opportunity updated successfully: " + updatedOpportunity.toString());
                    return updatedOpportunity;
                } else {
                    logger.log(Level.WARNING, "Opportunity not found with ID: " + opportunity.getOpportunityId());
                }
            } else {
                logger.log(Level.WARNING, "Opportunity ID cannot be null.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update opportunity: " + opportunity.toString(), e);
        }
        return null; // Return null or throw an exception based on your error handling strategy
    }

    public void deleteOpportunity(Long opportunityId) {
        opportunityRepository.deleteById(opportunityId);
        logger.log(Level.INFO, "Opportunity deleted with ID: " + opportunityId);
    }
}

