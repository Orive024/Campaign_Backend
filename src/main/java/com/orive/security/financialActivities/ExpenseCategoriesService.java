package com.orive.security.financialActivities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orive.security.donor.Donor;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ExpenseCategoriesService {

    private static final Logger logger = Logger.getLogger(ExpenseCategoriesService.class.getName());

    @Autowired
    private ExpenseCategoriesRepository expenseCategoriesRepository;

    public List<ExpenseCategories> getAllExpenseCategories() {
        logger.log(Level.INFO, "Fetching all expense categories");
        return expenseCategoriesRepository.findAll();
    }

    public ExpenseCategories getExpenseCategoryById(Long expensecategoriesId) {
        Optional<ExpenseCategories> expenseCategoryOptional = expenseCategoriesRepository.findById(expensecategoriesId);
        if (expenseCategoryOptional.isPresent()) {
            logger.log(Level.INFO, "Expense category found with ID: " + expensecategoriesId);
            return expenseCategoryOptional.get();
        } else {
            logger.log(Level.WARNING, "Expense category not found with ID: " + expensecategoriesId);
            return null;
        }
    }

    public ExpenseCategories createExpenseCategory(ExpenseCategories expenseCategory) {
        ExpenseCategories savedExpenseCategory = expenseCategoriesRepository.save(expenseCategory);
        logger.log(Level.INFO, "Expense category created: " + savedExpenseCategory.toString());
        return savedExpenseCategory;
    }

    public ExpenseCategories updateExpenseCategory(ExpenseCategories expenseCategory) {
    	try {
            if (expenseCategory.getExpensecategoriesId() != null) {
                Optional<ExpenseCategories> existingOpportunityOptional = expenseCategoriesRepository.findById(expenseCategory.getExpensecategoriesId());
                if (existingOpportunityOptional.isPresent()) {
                	ExpenseCategories existingOpportunity = existingOpportunityOptional.get();
                    if (expenseCategory.getCategory() != null) {
                        existingOpportunity.setCategory(expenseCategory.getCategory());
                    }
                    if (expenseCategory.getDescription() != null) {
                        existingOpportunity.setDescription(expenseCategory.getDescription());
                    }
                    
                    ExpenseCategories updatedOpportunity = expenseCategoriesRepository.save(existingOpportunity);
                    logger.log(Level.INFO, "expenseCategory updated successfully: " + updatedOpportunity.toString());
                    return updatedOpportunity;
                } else {
                    logger.log(Level.WARNING, "expenseCategory not found with ID: " + expenseCategory.getExpensecategoriesId());
                }
            } else {
                logger.log(Level.WARNING, "expenseCategory ID cannot be null.");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update expenseCategory: " + expenseCategory.toString(), e);
        }
        return null; // Return null or throw an exception based on your error handling strategy
    }

    public void deleteExpenseCategory(Long expensecategoriesId) {
        expenseCategoriesRepository.deleteById(expensecategoriesId);
        logger.log(Level.INFO, "Expense category deleted with ID: " + expensecategoriesId);
    }
}

