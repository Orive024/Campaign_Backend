package com.orive.security.financialActivities;

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
@RequestMapping("/expenses")
@Tag(name = "Expense")
@CrossOrigin(origins = "*")
public class ExpenseController {

    private static final Logger logger = Logger.getLogger(ExpenseController.class.getName());

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        logger.log(Level.INFO, "Fetched all expenses");
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{expenseId}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long expenseId) {
        Expense expense = expenseService.getExpenseById(expenseId);
        if (expense != null) {
            logger.log(Level.INFO, "Expense found with ID: " + expenseId);
            return ResponseEntity.ok(expense);
        } else {
            logger.log(Level.WARNING, "Expense not found with ID: " + expenseId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        Expense createdExpense = expenseService.createExpense(expense);
        logger.log(Level.INFO, "Expense created: " + createdExpense.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    @PutMapping("/{expenseId}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long expenseId, @RequestBody Expense expense) {
        expense.setExpenseId(expenseId);
        Expense updatedExpense = expenseService.updateExpense(expense);
        logger.log(Level.INFO, "Expense updated: " + updatedExpense.toString());
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId);
        logger.log(Level.INFO, "Expense deleted with ID: " + expenseId);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/all-amounts")
    public ResponseEntity<List<BigDecimal>> getAllAmounts() {
        List<BigDecimal> amounts = expenseService.findAllAmounts();
        logger.log(Level.INFO, "All amounts endpoint accessed");
        return ResponseEntity.ok(amounts);
    }
    
    @GetMapping("/category-amount")
    public ResponseEntity<List<Object[]>> findAllCategoryAndAmount() {
        List<Object[]> categoryAndAmountList = expenseService.findAllCategoryAndAmount();
        return ResponseEntity.ok(categoryAndAmountList);
    }
}
