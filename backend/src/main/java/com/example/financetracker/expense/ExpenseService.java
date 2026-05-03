package com.example.financetracker.expense;

import com.example.financetracker.expense.dto.ExpenseRequest;
import com.example.financetracker.expense.dto.ExpenseResponse;
import com.example.financetracker.user.User;
import com.example.financetracker.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    @CacheEvict(value = "dashboard", key = "#request.userId")
    public ExpenseResponse addExpense(ExpenseRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setDescription(request.getDescription());
        expense.setUser(user);

        Expense saved = expenseRepository.save(expense);

        return mapToResponse(saved);
    }

    public List<ExpenseResponse> getUserExpenses(Long userId) {

        return expenseRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ExpenseResponse mapToResponse(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .date(expense.getDate())
                .description(expense.getDescription())
                .build();
    }

    @CacheEvict(value = "dashboard", key = "#userId")
    public void deleteExpense(Long userId, Long expenseId) {
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));

        if (expense.getUser() == null || expense.getUser().getId() == null || !expense.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not authorized to delete this expense");
        }

        expenseRepository.delete(expense);
    }
}
