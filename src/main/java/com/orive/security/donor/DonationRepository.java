package com.orive.security.donor;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
	
	 @Query("SELECT SUM(d.amount) FROM Donation d WHERE d.type = 'cash'")
	    BigDecimal getTotalCashAmount();

	    @Query("SELECT SUM(d.amount) FROM Donation d WHERE d.type = 'cheque'")
	    BigDecimal getTotalChequeAmount();

	    @Query("SELECT SUM(d.amount) FROM Donation d WHERE d.type NOT IN ('cash', 'cheque')")
	    BigDecimal getTotalOtherAmount();
	    
	    @Query("SELECT SUM(d.amount) FROM Donation d")
	    BigDecimal getTotalAmountOfAllDonations();
}

