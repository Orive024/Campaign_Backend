package com.orive.security.financialActivities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoriesRepository extends JpaRepository<ExpenseCategories, Long> {
}

