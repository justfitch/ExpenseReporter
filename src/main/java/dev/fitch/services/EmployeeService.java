package dev.fitch.services;

import dev.fitch.entities.Employee;

import java.util.List;

public interface EmployeeService {

    Employee createEmployee(Employee employee);

    Employee getEmployeeDetails(int id);

    List<Employee> getAllEmployees();

    Employee updateEmployeeRecord(Employee employee);

    boolean deleteEmployee(int id);

}
