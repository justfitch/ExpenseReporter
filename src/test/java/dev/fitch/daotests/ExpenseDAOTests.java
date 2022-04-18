package dev.fitch.daotests;

import dev.fitch.data.EmployeeDAO;
import dev.fitch.data.EmployeeDAOPostgresImpl;
import dev.fitch.data.ExpenseDAO;
import dev.fitch.data.ExpenseDAOPostgresImpl;
import dev.fitch.entities.Employee;
import dev.fitch.entities.Expense;
import org.junit.jupiter.api.*;

import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)// to run tests in order

public class ExpenseDAOTests {

    static ExpenseDAO expenseDAO = new ExpenseDAOPostgresImpl();
    static Expense testExpense = null;
    List<Expense> testExpenseList;
    static int expenseNumber1;
    static int expenseNumber2;
    public static int id1;
    public static int id2;


    static EmployeeDAO employeeDAO = new EmployeeDAOPostgresImpl();


    @Test
    @Order(1)
    void create_and_get_expense_details_test(){
        //CREATE AN EMPLOYEE TO TIE EXPENSES TO
        Employee fred = new Employee(0, "Fred", "Flintstone", "stoneage");
        Employee savedEmployee = employeeDAO.createEmployee(fred);
        id1 = savedEmployee.getId();

        Expense gas = new Expense(0, id1, 111.11, "Gas for travel", "Pending");
        Expense savedExpense = expenseDAO.createExpense(gas);
        expenseNumber1 = savedExpense.getExpenseNumber();

        testExpense = expenseDAO.getExpenseDetails(expenseNumber1);
        Assertions.assertNotNull(testExpense);
    }

    @Test
    @Order(2)
    void get_all_expenses_test(){
        Employee barney = new Employee(0, "Barney", "Rubble", "stoneage");
        Employee savedEmployee = employeeDAO.createEmployee(barney);
        id2 = savedEmployee.getId();

        Expense perDiem = new Expense(0, id2, 59.00, "1 Day of Per Diem- NM Standard Rate");
        Expense savedExpense = expenseDAO.createExpense(perDiem);
        expenseNumber2 = savedExpense.getExpenseNumber();

        testExpenseList = expenseDAO.getAllExpenses();
        Assertions.assertEquals(2, testExpenseList.size());
    }

    @Test
    @Order(3)
    void update_expense_test(){
        int id = EmployeeDaoTests.id1;
        Expense expense = new Expense(expenseNumber1, id1, 111.11, "Gasoline for Gallup Drive", "Approved");
        testExpense = expenseDAO.updateExpense(expense);

        Assertions.assertEquals("Approved", testExpense.getStatus());
    }

   @Test
    @Order(4)
    void delete_expense_test(){
        expenseDAO.deleteExpense(expenseNumber1);
        Assertions.assertTrue(expenseDAO.deleteExpense(expenseNumber2));

        employeeDAO.deleteEmployee(id1);
        employeeDAO.deleteEmployee(id2);
    }
}
