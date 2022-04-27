package dev.fitch.services;

import dev.fitch.data.ExpenseDAO;
import dev.fitch.data.ExpenseDAOPostgresImpl;
import dev.fitch.entities.Expense;

import java.util.List;

public class ExpenseServiceImpl implements ExpenseService{

    ExpenseDAO expenseDAO = new ExpenseDAOPostgresImpl();

    @Override
    public Expense createExpense(Expense expense) {
        return expenseDAO.createExpense(expense);
    }

    @Override
    public Expense getExpenseDetails(int expenseNumber) {
        return expenseDAO.getExpenseDetails(expenseNumber);
    }

    @Override
    public List<Expense> getAllExpenses() {
        return expenseDAO.getAllExpenses();
    }

    @Override
    public Expense updateExpense(Expense expense) {
        int expenseNumber = expense.getExpenseNumber();
        Expense statusCheck = expenseDAO.getExpenseDetails(expenseNumber);
        if (statusCheck.getStatus().equals("Pending")) {
            return expenseDAO.updateExpense(expense);
        } else {
            return null;
        }
    }

    @Override
    public Expense approveExpense(int expenseNumber) {
        Expense expense = expenseDAO.getExpenseDetails(expenseNumber);
        if (expense.getStatus().equals("Pending")) {
            expense.setStatus("Approved");
            expenseDAO.updateExpense(expense);
            return expense;
        } else {
            return null;
        }
    }

    @Override
    public Expense denyExpense(int expenseNumber) {
        Expense expense = expenseDAO.getExpenseDetails(expenseNumber);
        if (expense.getStatus().equals("Pending")) {
            expense.setStatus("Denied");
            expenseDAO.updateExpense(expense);
            return expense;
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteExpense(int expenseNumber) {
        Expense expense = expenseDAO.getExpenseDetails(expenseNumber);
        if (expense.getStatus().equals("Pending")) {
            return expenseDAO.deleteExpense(expenseNumber);
        } else {
            return false;
        }
    }
}
