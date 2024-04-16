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
@RequestMapping("/threats")
@Tag(name = "Threat")
@CrossOrigin(origins = "*")
public class ThreatController {

    private static final Logger logger = Logger.getLogger(ThreatController.class.getName());

    @Autowired
    private ThreatService threatService;

    @GetMapping
    public ResponseEntity<List<Threat>> getAllThreats() {
        List<Threat> threats = threatService.getAllThreats();
        logger.log(Level.INFO, "Fetched all threats");
        return ResponseEntity.ok(threats);
    }

    @GetMapping("/{threatId}")
    public ResponseEntity<Threat> getThreatById(@PathVariable Long threatId) {
        Threat threat = threatService.getThreatById(threatId);
        if (threat != null) {
            logger.log(Level.INFO, "Threat found with ID: " + threatId);
            return ResponseEntity.ok(threat);
        } else {
            logger.log(Level.WARNING, "Threat not found with ID: " + threatId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Threat> createThreat(@RequestBody Threat threat) {
        Threat createdThreat = threatService.createThreat(threat);
        logger.log(Level.INFO, "Threat created: " + createdThreat.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdThreat);
    }

    @PutMapping("/{threatId}")
    public ResponseEntity<Threat> updateThreat(@PathVariable Long threatId, @RequestBody Threat threat) {
        threat.setThreatId(threatId);
        Threat updatedThreat = threatService.updateThreat(threat);
        logger.log(Level.INFO, "Threat updated: " + updatedThreat.toString());
        return ResponseEntity.ok(updatedThreat);
    }

    @DeleteMapping("/{threatId}")
    public ResponseEntity<Void> deleteThreat(@PathVariable Long threatId) {
        threatService.deleteThreat(threatId);
        logger.log(Level.INFO, "Threat deleted with ID: " + threatId);
        return ResponseEntity.noContent().build();
    }
}
