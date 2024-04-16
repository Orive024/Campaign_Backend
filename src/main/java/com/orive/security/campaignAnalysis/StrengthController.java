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
@RequestMapping("/strengths")
@Tag(name = "Strength")
@CrossOrigin(origins = "*")
public class StrengthController {

    private static final Logger logger = Logger.getLogger(StrengthController.class.getName());

    @Autowired
    private StrengthService strengthService;

    @GetMapping
    public ResponseEntity<List<Strength>> getAllStrengths() {
        List<Strength> strengths = strengthService.getAllStrengths();
        logger.log(Level.INFO, "Fetched all strengths");
        return ResponseEntity.ok(strengths);
    }

    @GetMapping("/{strengthId}")
    public ResponseEntity<Strength> getStrengthById(@PathVariable Long strengthId) {
        Strength strength = strengthService.getStrengthById(strengthId);
        if (strength != null) {
            logger.log(Level.INFO, "Strength found with ID: " + strengthId);
            return ResponseEntity.ok(strength);
        } else {
            logger.log(Level.WARNING, "Strength not found with ID: " + strengthId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Strength> createStrength(@RequestBody Strength strength) {
        Strength createdStrength = strengthService.createStrength(strength);
        logger.log(Level.INFO, "Strength created: " + createdStrength.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStrength);
    }

    @PutMapping("/{strengthId}")
    public ResponseEntity<Strength> updateStrength(@PathVariable Long strengthId, @RequestBody Strength strength) {
        strength.setStrengthId(strengthId);
        Strength updatedStrength = strengthService.updateStrength(strength);
        logger.log(Level.INFO, "Strength updated: " + updatedStrength.toString());
        return ResponseEntity.ok(updatedStrength);
    }

    @DeleteMapping("/{strengthId}")
    public ResponseEntity<Void> deleteStrength(@PathVariable Long strengthId) {
        strengthService.deleteStrength(strengthId);
        logger.log(Level.INFO, "Strength deleted with ID: " + strengthId);
        return ResponseEntity.noContent().build();
    }
}

