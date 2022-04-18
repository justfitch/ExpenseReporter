package dev.fitch.services;

import dev.fitch.data.EmployeeDAO;
import dev.fitch.data.EmployeeDAOPostgresImpl;
import dev.fitch.entities.Employee;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService{

    public EmployeeDAO employeeDAO = new EmployeeDAOPostgresImpl();

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeDAO.createEmployee(employee);
    }

    @Override
    public Employee getEmployeeDetails(int id) {
        return employeeDAO.getEmployeeDetails(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.getAllEmployees();
    }

    @Override
    public Employee updateEmployeeRecord(Employee employee) {
        return employeeDAO.updateEmployeeRecord(employee);
    }

    @Override
    public boolean deleteEmployee(int id) {
        return employeeDAO.deleteEmployee(id);
    }
}
