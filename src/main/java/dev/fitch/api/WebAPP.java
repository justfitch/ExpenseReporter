package dev.fitch.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import dev.fitch.data.EmployeeDAOPostgresImpl;
import dev.fitch.entities.Employee;
import dev.fitch.entities.Expense;
import dev.fitch.services.EmployeeService;
import dev.fitch.services.EmployeeServiceImpl;
import dev.fitch.services.ExpenseService;
import dev.fitch.services.ExpenseServiceImpl;
import io.javalin.Javalin;

import java.util.ArrayList;
import java.util.List;

public class WebAPP {

    public static EmployeeService employeeService = new EmployeeServiceImpl();
    public static ExpenseService expenseService = new ExpenseServiceImpl();
    public static Gson gson = new Gson();

    public static void main(String[] args) {

        Javalin app = Javalin.create().start(7000);

        //CREATE EMPLOYEE
        app.post("/employees", context -> {
           String body = context.body();
           Employee employee = gson.fromJson(body, Employee.class);
           context.status(201);
           context.result(gson.toJson(employeeService.createEmployee(employee)));
        });

        //CREATE EXPENSE
        app.post("/expenses", context -> {
            String body = context.body();
            Expense expense = gson.fromJson(body, Expense.class);
            context.status(201);
            context.result(gson.toJson(expenseService.createExpense(expense)));
        });

        //READ/GET ONE EMPLOYEE
        app.get("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            try {
                String employeeJSON = gson.toJson(employeeService.getEmployeeDetails(id));
                context.result(employeeJSON);
            } catch (Exception e) {                                     //Why can't I made this ResourceNotFound?????
                context.status(404);
                context.result("User ID " + id + " was not found");
            }
        });

        //GET ALL EMPLOYEES
        app.get("/employees", context -> {
            List<Employee> allEmployees = employeeService.getAllEmployees();
            String allEmployeesJson = gson.toJson(allEmployees);
            context.result(allEmployeesJson);
        });

        //GET AN EXPENSE BY EXPENSENUMBER
        app.get("/expenses/{expenseNumber}", context -> {
            int expenseNumber = Integer.parseInt(context.pathParam("expenseNumber"));

            try {
                String expenseJSON = gson.toJson(expenseService.getExpenseDetails(expenseNumber));
                context.result(expenseJSON);
            } catch (Exception e) {
                context.status(404);
                context.result("Expense Number " + expenseNumber + " not found");
            }
        });

        //GET ALL EXPENSES BY STATUS (All if no parameter entered)
        app.get("/expenses", context -> {
            String status = context.queryParam("status");
            List<Expense> allExpenses = expenseService.getAllExpenses();

            if (status == null){
                context.result(gson.toJson(allExpenses));
            } else {
                List<Expense> requestedStatus = new ArrayList();
                for (Expense expense : allExpenses) {
                    if (expense.getStatus().equals(status)) {
                        requestedStatus.add(expense);
                    }
                }
                String resultsJson = gson.toJson(requestedStatus);
                context.result(resultsJson);
            }
;        });

        //UPDATE
        //PUT - REPLACE EMPLOYEE
        app.put("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));

            try {
                String body = context.body();
                Employee employee = gson.fromJson(body, Employee.class);
                employee.setId(id);
                employeeService.updateEmployeeRecord(employee);
                context.result("Employee Record Replaced");
            } catch (JsonSyntaxException e) {
                context.status(404);
                context.result("Employee ID " + id + " was not found");
            }
        });

        //PUT - REPLACE EXPENSE
        app.put("/expenses/{expenseNumber}", context -> {
            int expenseNumber = Integer.parseInt(context.pathParam("expenseNumber"));

            try {
                String body = context.body();
                Expense expense = gson.fromJson(body, Expense.class);
                expense.setExpenseNumber(expenseNumber);
                expenseService.updateExpense(expense);
                context.result("Expense Report Updated");
            } catch (JsonSyntaxException e) {
                context.status(404);
                context.result("Expense number " + expenseNumber + " was not found");
            }
        });

        //PATCH - Update Expense to Approved
        app.patch("/expenses/{expenseNumber}/approve", context -> {
            int expenseNumber = Integer.parseInt(context.pathParam("expenseNumber"));
            Expense expense = expenseService.approveExpense(expenseNumber);
            if (expense == null) {
                context.status(404);
                context.result("Expense number " + expenseNumber + " was not found.");
            } else {
                context.result("Expense number " + expenseNumber + " approved.");
            }
        });

            //PATCH - Update Expense to Denied
            app.patch("/expenses/{expenseNumber}/deny", context -> {
                int expenseNumber = Integer.parseInt(context.pathParam("expenseNumber"));
                Expense expense = expenseService.denyExpense(expenseNumber);
                if (expense == null){
                    context.status(404);
                    context.result("Expense number " + expenseNumber + " was not found.");
                } else {
                    context.result("Expense number " + expenseNumber + " denied.");
                }
            });

        //DELETE
        //DELETE EMPLOYEE
        app.delete("/employees/{id}", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            boolean result = employeeService.deleteEmployee(id);
            if (result) {
                context.status(204);
                context.result("Employee record deleted");
            } else {
                context.status(500);
                context.result("Employee id " + id + " not found");
            }
        });

        //DELETE EXPENSE
        app.delete("/expenses/{expenseNumber}", context -> {
            int expenseNumber = Integer.parseInt(context.pathParam("expenseNumber"));
            boolean result = expenseService.deleteExpense(expenseNumber);
            if (result){
                context.status(204);
                context.result("Expense deleted");
            } else {
                context.status(500);
                context.result("Expense number " + expenseNumber + " not found.");
            }
        });

        //NESTED
        app.get("/employees{id}/expenses", context -> {
            int id = Integer.parseInt(context.pathParam("id"));
            //I NEED TO CREATE A METHOD IN EXPENSE SERVICE TO GET ALL EXPENSES BY EMPLOYEE ID
        });
    }
}
