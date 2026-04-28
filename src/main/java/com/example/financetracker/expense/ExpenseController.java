package com.example.financetracker.expense;

import com.example.financetracker.expense.dto.ExpenseRequest;
import com.example.financetracker.expense.dto.ExpenseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    // ✅ Add expense
    @PostMapping
    public ResponseEntity<ExpenseResponse> addExpense(
            @Valid @RequestBody ExpenseRequest request) {

        return ResponseEntity.ok(expenseService.addExpense(request));
    }

    // ✅ Get all expenses for user
    @GetMapping("/{userId}")
    public ResponseEntity<List<ExpenseResponse>> getExpenses(
            @PathVariable Long userId) {

        return ResponseEntity.ok(expenseService.getUserExpenses(userId));
    }

    // ✅ Delete an expense for a user
    @DeleteMapping("/{userId}/{expenseId}")
    public ResponseEntity<Void> deleteExpense(
            @PathVariable Long userId,
            @PathVariable Long expenseId) {

        expenseService.deleteExpense(userId, expenseId);
        return ResponseEntity.noContent().build();
    }
}
