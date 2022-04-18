package dev.fitch.data;

import dev.fitch.entities.Employee;

import java.util.List;

public interface EmployeeDAO {

    //CREATE (POST /EMPLOYEES)
    Employee createEmployee(Employee employee);

    //READ
    Employee getEmployeeDetails(int id); //(GET /EMPLOYEES/120)

    List<Employee> getAllEmployees(); //(GET /EMPLOYEES)

    //UPDATE  (PUT /EMPLOYEES/150)
    Employee updateEmployeeRecord(Employee employee);

    //DELETE  (DELETE /EMPLOYEES/190)
    boolean deleteEmployee(int id);

}
