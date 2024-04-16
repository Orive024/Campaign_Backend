package com.orive.security.donor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/donors")
@Tag(name = "Donor")
@CrossOrigin(origins = "*")
public class DonorController {

    private static final Logger logger = Logger.getLogger(DonorController.class.getName());

    @Autowired
    private DonorService donorService;

    @GetMapping
    public ResponseEntity<List<Donor>> getAllDonors() {
        List<Donor> donors = donorService.getAllDonors();
        logger.log(Level.INFO, "Fetched all donors");
        return ResponseEntity.ok(donors);
    }

    @GetMapping("/{donorId}")
    public ResponseEntity<Donor> getDonorById(@PathVariable Long donorId) {
        Donor donor = donorService.getDonorById(donorId);
        if (donor != null) {
            logger.log(Level.INFO, "Donor found with ID: " + donorId);
            return ResponseEntity.ok(donor);
        } else {
            logger.log(Level.WARNING, "Donor not found with ID: " + donorId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Donor> createDonor(@RequestBody Donor donor) {
        Donor createdDonor = donorService.createDonor(donor);
        logger.log(Level.INFO, "Donor created: " + createdDonor.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDonor);
    }

    @PutMapping("/{donorId}")
    public ResponseEntity<Donor> updateDonor(@PathVariable Long donorId, @RequestBody Donor donor) {
        donor.setDonorId(donorId);
        Donor updatedDonor = donorService.updateDonor(donor);
        logger.log(Level.INFO, "Donor updated: " + updatedDonor.toString());
        return ResponseEntity.ok(updatedDonor);
    }

    @DeleteMapping("/{donorId}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Long donorId) {
        donorService.deleteDonor(donorId);
        logger.log(Level.INFO, "Donor deleted with ID: " + donorId);
        return ResponseEntity.noContent().build();
    }
}
