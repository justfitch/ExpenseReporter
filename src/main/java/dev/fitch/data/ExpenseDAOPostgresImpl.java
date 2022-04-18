package dev.fitch.data;

import dev.fitch.entities.Expense;
import dev.fitch.utilities.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAOPostgresImpl implements ExpenseDAO{


    @Override
    public Expense createExpense(Expense expense) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into expense values (default, ?,?,?,?);";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, expense.getEmployeeId());
            ps.setDouble(2, expense.getAmount());
            ps.setString(3, expense.getDescription());
            ps.setString(4, "Pending");

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            expense.setExpenseNumber(rs.getInt("expense_number"));

            return expense;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Expense getExpenseDetails(int expenseNumber) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from expense where expense_number = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, expenseNumber);

            ResultSet rs = ps.executeQuery();
            rs.next();

            Expense expense = new Expense();

            expense.setExpenseNumber(rs.getInt("expense_number"));
            expense.setEmployeeId(rs.getInt("employee_id"));
            expense.setAmount(rs.getDouble("amount"));
            expense.setDescription(rs.getString("expense_desc"));
            expense.setStatus(rs.getString("status"));

            return expense;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Expense> getAllExpenses() {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from expense;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            List<Expense> expenses = new ArrayList();
            while (rs.next()){
                Expense expense = new Expense();

                expense.setExpenseNumber(rs.getInt("expense_number"));
                expense.setEmployeeId(rs.getInt("employee_id"));
                expense.setAmount(rs.getDouble("amount"));
                expense.setDescription(rs.getString("expense_desc"));
                expense.setStatus(rs.getString("status"));

                expenses.add(expense);
            }

            return expenses;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Expense updateExpense(Expense expense) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update expense set employee_id = ?, amount = ?, expense_desc = ?, status = ? where expense_number = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, expense.getEmployeeId());
            ps.setDouble(2, expense.getAmount());
            ps.setString(3, expense.getDescription());
            ps.setString(4, expense.getStatus());
            ps.setInt(5, expense.getExpenseNumber());

            ps.executeUpdate();

            return expense;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteExpense(int expenseNumber) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from expense where expense_number = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, expenseNumber);

            ps.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
