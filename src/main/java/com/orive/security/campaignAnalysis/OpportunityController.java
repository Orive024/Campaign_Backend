package com.orive.security.campaignAnalysis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/opportunities")
@Tag(name = "Opportunity")
@CrossOrigin(origins = "*")
public class OpportunityController {

    private static final Logger logger = Logger.getLogger(OpportunityController.class.getName());

    @Autowired
    private OpportunityService opportunityService;

    @GetMapping
    public ResponseEntity<List<Opportunity>> getAllOpportunities() {
        List<Opportunity> opportunities = opportunityService.getAllOpportunities();
        logger.log(Level.INFO, "Fetched all opportunities");
        return ResponseEntity.ok(opportunities);
    }

    @GetMapping("/{opportunityId}")
    public ResponseEntity<Opportunity> getOpportunityById(@PathVariable Long opportunityId) {
        Opportunity opportunity = opportunityService.getOpportunityById(opportunityId);
        if (opportunity != null) {
            logger.log(Level.INFO, "Opportunity found with ID: " + opportunityId);
            return ResponseEntity.ok(opportunity);
        } else {
            logger.log(Level.WARNING, "Opportunity not found with ID: " + opportunityId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Opportunity> createOpportunity(@RequestBody Opportunity opportunity) {
        Opportunity createdOpportunity = opportunityService.createOpportunity(opportunity);
        logger.log(Level.INFO, "Opportunity created: " + createdOpportunity.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOpportunity);
    }

    @PutMapping("/{opportunityId}")
    public ResponseEntity<Opportunity> updateOpportunity(@PathVariable Long opportunityId, @RequestBody Opportunity opportunity) {
        opportunity.setOpportunityId(opportunityId);
        Opportunity updatedOpportunity = opportunityService.updateOpportunity(opportunity);
        logger.log(Level.INFO, "Opportunity updated: " + updatedOpportunity.toString());
        return ResponseEntity.ok(updatedOpportunity);
    }

    @DeleteMapping("/{opportunityId}")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable Long opportunityId) {
        opportunityService.deleteOpportunity(opportunityId);
        logger.log(Level.INFO, "Opportunity deleted with ID: " + opportunityId);
        return ResponseEntity.noContent().build();
    }
}

