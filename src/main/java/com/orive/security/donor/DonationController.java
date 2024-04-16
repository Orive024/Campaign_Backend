package com.orive.security.donor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/donations")
@Tag(name = "Donation")
@CrossOrigin(origins = "*")
public class DonationController {

    private static final Logger logger = Logger.getLogger(DonationController.class.getName());

    @Autowired
    private DonationService donationService;

    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonations() {
        List<Donation> donations = donationService.getAllDonations();
        logger.log(Level.INFO, "Fetched all donations");
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/{donationId}")
    public ResponseEntity<Donation> getDonationById(@PathVariable Long donationId) {
        Donation donation = donationService.getDonationById(donationId);
        if (donation != null) {
            logger.log(Level.INFO, "Donation found with ID: " + donationId);
            return ResponseEntity.ok(donation);
        } else {
            logger.log(Level.WARNING, "Donation not found with ID: " + donationId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody Donation donation) {
        Donation createdDonation = donationService.createDonation(donation);
        logger.log(Level.INFO, "Donation created: " + createdDonation.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDonation);
    }

    @PutMapping("/{donationId}")
    public ResponseEntity<Donation> updateDonation(@PathVariable Long donationId, @RequestBody Donation donation) {
        donation.setDonationId(donationId);
        Donation updatedDonation = donationService.updateDonation(donation);
        logger.log(Level.INFO, "Donation updated: " + updatedDonation.toString());
        return ResponseEntity.ok(updatedDonation);
    }

    @DeleteMapping("/{donationId}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long donationId) {
        donationService.deleteDonation(donationId);
        logger.log(Level.INFO, "Donation deleted with ID: " + donationId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/cash-amount")
    public ResponseEntity<BigDecimal> getTotalCashAmount() {
        BigDecimal totalCashAmount = donationService.getTotalCashAmount();
        logger.log(Level.INFO, "Total cash amount retrieved");
        return ResponseEntity.ok(totalCashAmount);
    }

    @GetMapping("/cheque-amount")
    public ResponseEntity<BigDecimal> getTotalChequeAmount() {
        BigDecimal totalChequeAmount = donationService.getTotalChequeAmount();
        logger.log(Level.INFO, "Total cheque amount retrieved");
        return ResponseEntity.ok(totalChequeAmount);
    }

    @GetMapping("/other-amount")
    public ResponseEntity<BigDecimal> getTotalOtherAmount() {
        BigDecimal totalOtherAmount = donationService.getTotalOtherAmount();
        logger.log(Level.INFO, "Total other amount retrieved");
        return ResponseEntity.ok(totalOtherAmount);
    }
    
    @GetMapping("/total-amount")
    public ResponseEntity<BigDecimal> getTotalAmountOfAllDonations() {
        BigDecimal totalAmount = donationService.getTotalAmountOfAllDonations();
        logger.log(Level.INFO, "Total amount of all donations retrieved");
        return ResponseEntity.ok(totalAmount);
    }
}
