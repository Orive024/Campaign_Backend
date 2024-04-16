package com.orive.security.financialActivities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/expensecategories")
@Tag(name = "ExpenseCategories")
@CrossOrigin(origins = "*")
public class ExpenseCategoriesController {

    private static final Logger logger = Logger.getLogger(ExpenseCategoriesController.class.getName());

    @Autowired
    private ExpenseCategoriesService expenseCategoriesService;

    @GetMapping
    public ResponseEntity<List<ExpenseCategories>> getAllExpenseCategories() {
        List<ExpenseCategories> expenseCategories = expenseCategoriesService.getAllExpenseCategories();
        logger.log(Level.INFO, "Fetched all expense categories");
        return ResponseEntity.ok(expenseCategories);
    }

    @GetMapping("/{expensecategoriesId}")
    public ResponseEntity<ExpenseCategories> getExpenseCategoryById(@PathVariable Long expensecategoriesId) {
        ExpenseCategories expenseCategory = expenseCategoriesService.getExpenseCategoryById(expensecategoriesId);
        if (expenseCategory != null) {
            logger.log(Level.INFO, "Expense category found with ID: " + expensecategoriesId);
            return ResponseEntity.ok(expenseCategory);
        } else {
            logger.log(Level.WARNING, "Expense category not found with ID: " + expensecategoriesId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ExpenseCategories> createExpenseCategory(@RequestBody ExpenseCategories expenseCategory) {
        ExpenseCategories createdExpenseCategory = expenseCategoriesService.createExpenseCategory(expenseCategory);
        logger.log(Level.INFO, "Expense category created: " + createdExpenseCategory.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpenseCategory);
    }

    @PutMapping("/{expensecategoriesId}")
    public ResponseEntity<ExpenseCategories> updateExpenseCategory(@PathVariable Long expensecategoriesId, @RequestBody ExpenseCategories expenseCategory) {
        expenseCategory.setExpensecategoriesId(expensecategoriesId);
        ExpenseCategories updatedExpenseCategory = expenseCategoriesService.updateExpenseCategory(expenseCategory);
        logger.log(Level.INFO, "Expense category updated: " + updatedExpenseCategory.toString());
        return ResponseEntity.ok(updatedExpenseCategory);
    }

    @DeleteMapping("/{expensecategoriesId}")
    public ResponseEntity<Void> deleteExpenseCategory(@PathVariable Long expensecategoriesId) {
        expenseCategoriesService.deleteExpenseCategory(expensecategoriesId);
        logger.log(Level.INFO, "Expense category deleted with ID: " + expensecategoriesId);
        return ResponseEntity.noContent().build();
    }
}
