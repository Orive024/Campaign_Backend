package com.orive.security.campaignAnalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ThreatService {

    private static final Logger logger = Logger.getLogger(ThreatService.class.getName());

    @Autowired
    private ThreatRepository threatRepository;

    public List<Threat> getAllThreats() {
        logger.log(Level.INFO, "Fetching all threats");
        return threatRepository.findAll();
    }

    public Threat getThreatById(Long threatId) {
        Optional<Threat> threatOptional = threatRepository.findById(threatId);
        if (threatOptional.isPresent()) {
            logger.log(Level.INFO, "Threat found with ID: " + threatId);
            return threatOptional.get();
        } else {
            logger.log(Level.WARNING, "Threat not found with ID: " + threatId);
            return null;
        }
    }

    public Threat createThreat(Threat threat) {
        Threat savedThreat = threatRepository.save(threat);
        logger.log(Level.INFO, "Threat created: " + savedThreat.toString());
        return savedThreat;
    }

    public Threat updateThreat(Threat threat) {
    	try {
            if (threat.getThreatId() != null) {
                Optional<Threat> existingOpportunityOptional = threatRepository.findById(threat.getThreatId());
                if (existingOpportunityOptional.isPresent()) {
                	Threat existingOpportunity = existingOpportunityOptional.get();
                    if (threat.getThreat() != null) {
                        existingOpportunity.setThreat(threat.getThreat());
                    }
                    if (threat.getNote() != null) {
                        existingOpportunity.setNote(threat.getNote());
                    }
                    Threat updatedOpportunity = threatRepository.save(existingOpportunity);
                    logger.log(Level.INFO, "threat updated successfully: " + updatedOpportunity.toString());
                    return updatedOpportunity;
                } else {
                    logger.log(Level.WARNING, "threat not found with ID: " + threat.getThreatId());
                }
            } else {
                logger.log(Level.WARNING, "threat ID cannot be null.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update threat: " + threat.toString(), e);
        }
        return null; // Return null or throw an exception based on your error handling strategy
    }

    public void deleteThreat(Long threatId) {
        threatRepository.deleteById(threatId);
        logger.log(Level.INFO, "Threat deleted with ID: " + threatId);
    }
}
