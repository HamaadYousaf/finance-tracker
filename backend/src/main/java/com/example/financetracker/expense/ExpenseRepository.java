package com.example.financetracker.expense;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserId(Long userId);

    List<Expense> findByUser_IdAndDateBetween(
            Long userId,
            LocalDate start,
            LocalDate end
    );

    List<Expense> findByUser_Id(Long userId);
}
