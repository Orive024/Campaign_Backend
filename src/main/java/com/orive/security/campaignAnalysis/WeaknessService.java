package com.orive.security.campaignAnalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class WeaknessService {

    private static final Logger logger = Logger.getLogger(WeaknessService.class.getName());

    @Autowired
    private WeaknessRepository weaknessRepository;

    public List<Weakness> getAllWeaknesses() {
        logger.log(Level.INFO, "Fetching all weaknesses");
        return weaknessRepository.findAll();
    }

    public Weakness getWeaknessById(Long weaknessId) {
        Optional<Weakness> weaknessOptional = weaknessRepository.findById(weaknessId);
        if (weaknessOptional.isPresent()) {
            logger.log(Level.INFO, "Weakness found with ID: " + weaknessId);
            return weaknessOptional.get();
        } else {
            logger.log(Level.WARNING, "Weakness not found with ID: " + weaknessId);
            return null;
        }
    }

    public Weakness createWeakness(Weakness weakness) {
        Weakness savedWeakness = weaknessRepository.save(weakness);
        logger.log(Level.INFO, "Weakness created: " + savedWeakness.toString());
        return savedWeakness;
    }

    public Weakness updateWeakness(Weakness weakness) {
    	try {
            if (weakness.getWeaknessId() != null) {
                Optional<Weakness> existingOpportunityOptional = weaknessRepository.findById(weakness.getWeaknessId());
                if (existingOpportunityOptional.isPresent()) {
                	Weakness existingOpportunity = existingOpportunityOptional.get();
                    if (weakness.getWeakness() != null) {
                        existingOpportunity.setWeakness(weakness.getWeakness());
                    }
                    if (weakness.getNote() != null) {
                        existingOpportunity.setNote(weakness.getNote());
                    }
                    Weakness updatedOpportunity = weaknessRepository.save(existingOpportunity);
                    logger.log(Level.INFO, "weakness updated successfully: " + updatedOpportunity.toString());
                    return updatedOpportunity;
                } else {
                    logger.log(Level.WARNING, "weakness not found with ID: " + weakness.getWeaknessId());
                }
            } else {
                logger.log(Level.WARNING, "weakness ID cannot be null.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update weakness: " + weakness.toString(), e);
        }
        return null; // Return null or throw an exception based on your error handling strategy
    }

    public void deleteWeakness(Long weaknessId) {
        weaknessRepository.deleteById(weaknessId);
        logger.log(Level.INFO, "Weakness deleted with ID: " + weaknessId);
    }
}

