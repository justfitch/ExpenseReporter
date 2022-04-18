package dev.fitch.services;

import dev.fitch.entities.Expense;

import java.util.List;

public interface ExpenseService {

    Expense createExpense(Expense expense);

    Expense getExpenseDetails(int expenseNumber);

    List<Expense> getAllExpenses();

    Expense updateExpense(Expense expense);

    Expense approveExpense(int expenseNumber);

    Expense denyExpense(int expenseNumber);

    boolean deleteExpense(int expenseNumber);

}
