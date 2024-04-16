package com.orive.security.financialActivities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ExpenseService {

    private static final Logger logger = Logger.getLogger(ExpenseService.class.getName());

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        logger.log(Level.INFO, "Fetching all expenses");
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long expenseId) {
        Optional<Expense> expenseOptional = expenseRepository.findById(expenseId);
        if (expenseOptional.isPresent()) {
            logger.log(Level.INFO, "Expense found with ID: " + expenseId);
            return expenseOptional.get();
        } else {
            logger.log(Level.WARNING, "Expense not found with ID: " + expenseId);
            return null;
        }
    }

    public Expense createExpense(Expense expense) {
        Expense savedExpense = expenseRepository.save(expense);
        logger.log(Level.INFO, "Expense created: " + savedExpense.toString());
        return savedExpense;
    }

    public Expense updateExpense(Expense expense) {
        Expense updatedExpense = expenseRepository.save(expense);
        logger.log(Level.INFO, "Expense updated: " + updatedExpense.toString());
        return updatedExpense;
    }

    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
        logger.log(Level.INFO, "Expense deleted with ID: " + expenseId);
    }
    
    public List<BigDecimal> findAllAmounts() {
        List<BigDecimal> amounts = expenseRepository.findAllAmounts();
        logger.log(Level.INFO, "All amounts retrieved: " + amounts);
        return amounts;
    }
    
    public List<Object[]> findAllCategoryAndAmount() {
        return expenseRepository.findAllCategoryAndAmount();
    }
}

