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
@RequestMapping("/weaknesses")
@Tag(name = "Weakness")
@CrossOrigin(origins = "*")
public class WeaknessController {

    private static final Logger logger = Logger.getLogger(WeaknessController.class.getName());

    @Autowired
    private WeaknessService weaknessService;

    @GetMapping
    public ResponseEntity<List<Weakness>> getAllWeaknesses() {
        List<Weakness> weaknesses = weaknessService.getAllWeaknesses();
        logger.log(Level.INFO, "Fetched all weaknesses");
        return ResponseEntity.ok(weaknesses);
    }

    @GetMapping("/{weaknessId}")
    public ResponseEntity<Weakness> getWeaknessById(@PathVariable Long weaknessId) {
        Weakness weakness = weaknessService.getWeaknessById(weaknessId);
        if (weakness != null) {
            logger.log(Level.INFO, "Weakness found with ID: " + weaknessId);
            return ResponseEntity.ok(weakness);
        } else {
            logger.log(Level.WARNING, "Weakness not found with ID: " + weaknessId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Weakness> createWeakness(@RequestBody Weakness weakness) {
        Weakness createdWeakness = weaknessService.createWeakness(weakness);
        logger.log(Level.INFO, "Weakness created: " + createdWeakness.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWeakness);
    }

    @PutMapping("/{weaknessId}")
    public ResponseEntity<Weakness> updateWeakness(@PathVariable Long weaknessId, @RequestBody Weakness weakness) {
        weakness.setWeaknessId(weaknessId);
        Weakness updatedWeakness = weaknessService.updateWeakness(weakness);
        logger.log(Level.INFO, "Weakness updated: " + updatedWeakness.toString());
        return ResponseEntity.ok(updatedWeakness);
    }

    @DeleteMapping("/{weaknessId}")
    public ResponseEntity<Void> deleteWeakness(@PathVariable Long weaknessId) {
        weaknessService.deleteWeakness(weaknessId);
        logger.log(Level.INFO, "Weakness deleted with ID: " + weaknessId);
        return ResponseEntity.noContent().build();
    }
}

