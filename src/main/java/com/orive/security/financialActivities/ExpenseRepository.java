package com.orive.security.financialActivities;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	
	@Query("SELECT e.amount FROM Expense e")
    List<BigDecimal> findAllAmounts();
	
	@Query("SELECT e.category, e.amount FROM Expense e")
    List<Object[]> findAllCategoryAndAmount();
}

