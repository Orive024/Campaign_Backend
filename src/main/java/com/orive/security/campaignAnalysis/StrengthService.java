package com.orive.security.campaignAnalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class StrengthService {

    private static final Logger logger = Logger.getLogger(StrengthService.class.getName());

    @Autowired
    private StrengthRepository strengthRepository;

    public List<Strength> getAllStrengths() {
        logger.log(Level.INFO, "Fetching all strengths");
        return strengthRepository.findAll();
    }

    public Strength getStrengthById(Long strengthId) {
        Optional<Strength> strengthOptional = strengthRepository.findById(strengthId);
        if (strengthOptional.isPresent()) {
            logger.log(Level.INFO, "Strength found with ID: " + strengthId);
            return strengthOptional.get();
        } else {
            logger.log(Level.WARNING, "Strength not found with ID: " + strengthId);
            return null;
        }
    }

    public Strength createStrength(Strength strength) {
        Strength savedStrength = strengthRepository.save(strength);
        logger.log(Level.INFO, "Strength created: " + savedStrength.toString());
        return savedStrength;
    }

    public Strength updateStrength(Strength strength) {
    	try {
            if (strength.getStrengthId() != null) {
                Optional<Strength> existingOpportunityOptional = strengthRepository.findById(strength.getStrengthId());
                if (existingOpportunityOptional.isPresent()) {
                	Strength existingOpportunity = existingOpportunityOptional.get();
                    if (strength.getStrength() != null) {
                        existingOpportunity.setStrength(strength.getStrength());
                    }
                    if (strength.getNote() != null) {
                        existingOpportunity.setNote(strength.getNote());
                    }
                    Strength updatedOpportunity = strengthRepository.save(existingOpportunity);
                    logger.log(Level.INFO, "strength updated successfully: " + updatedOpportunity.toString());
                    return updatedOpportunity;
                } else {
                    logger.log(Level.WARNING, "strength not found with ID: " + strength.getStrengthId());
                }
            } else {
                logger.log(Level.WARNING, "strength ID cannot be null.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update strength: " + strength.toString(), e);
        }
        return null; // Return null or throw an exception based on your error handling strategy
    }

    public void deleteStrength(Long strengthId) {
        strengthRepository.deleteById(strengthId);
        logger.log(Level.INFO, "Strength deleted with ID: " + strengthId);
    }
}

