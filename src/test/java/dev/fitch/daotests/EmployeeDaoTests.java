package dev.fitch.daotests;

import dev.fitch.data.EmployeeDAO;
import dev.fitch.data.EmployeeDAOPostgresImpl;
import dev.fitch.entities.Employee;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)// to run tests in order

public class EmployeeDaoTests {

    static EmployeeDAO employeeDAO = new EmployeeDAOPostgresImpl();
    static Employee testEmployee = null;
    List<Employee> testEmployeeList;
    public static int id1;
    public static int id2;

    @Test
    @Order(1)
    void create_and_get_employee_details_test(){
        Employee fred = new Employee(0, "Fred", "Flintstone", "stoneage");
        Employee savedEmployee = employeeDAO.createEmployee(fred);

        testEmployee = employeeDAO.getEmployeeDetails(savedEmployee.getId());
        id1 = testEmployee.getId();
        Assertions.assertNotNull(testEmployee);
    }

    @Test
    @Order(2)
    void get_all_employees_test(){
        Employee barney = new Employee(0, "Barney", "Rubble", "pebble");
        Employee savedEmployee = employeeDAO.createEmployee(barney);
        id2 = savedEmployee.getId();

        testEmployeeList = employeeDAO.getAllEmployees();
        Assertions.assertNotNull(testEmployeeList);
    }

    @Test
    @Order(3)
    void update_employee_test(){
        Employee employee = new Employee(id2, "Wilma", "Flintsone", "BamBam");
        employeeDAO.updateEmployeeRecord(employee);
    }

    @Test
    @Order(4)
    void delete_employee_test(){
        Assertions.assertTrue(employeeDAO.deleteEmployee(id1));
        Assertions.assertTrue(employeeDAO.deleteEmployee(id2));
    }
}
