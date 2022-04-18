package dev.fitch.data;

import dev.fitch.entities.Expense;

import java.util.List;

public interface ExpenseDAO {

    //CREATE (POST /EXPENSES)
    Expense createExpense(Expense expense);

    //READ (GET EXPENSES/12)
    Expense getExpenseDetails(int expenseNumber);

    List<Expense> getAllExpenses();  // (GET /EXPENSES)

    //UPDATE (PUT /EXPENSES/15) (PATCH /EXPENSES/20/APPROVE) (PATCH /EXPENSES/20/DENY)
    Expense updateExpense(Expense expense);

    //DELETE (DELETE /EXPENSES/19)
    boolean deleteExpense(int expenseNumber);
}
