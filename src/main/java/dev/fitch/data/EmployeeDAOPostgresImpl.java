package dev.fitch.data;

import dev.fitch.entities.Employee;
import dev.fitch.utilities.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOPostgresImpl implements EmployeeDAO{


    @Override
    public Employee createEmployee(Employee employee) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "insert into employee values (default,?,?,?);"; //"default" is for the generated ID
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getPassword());

            ps.execute(); //send "sql" SQL statement to db to create

            ResultSet rs = ps.getGeneratedKeys();
            rs.next(); //move to the first record
            employee.setId(rs.getInt("id")); //SET ID IN EMPLOYEE OBJECT TO GENERATED KEY
            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee getEmployeeDetails(int id) {
        Employee employee = null;
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from employee where id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();
            employee = new Employee();
            employee.setId(rs.getInt("id"));
            employee.setFirstName(rs.getString("first_name"));
            employee.setLastName(rs.getString("last_name"));
            employee.setPassword(rs.getString("user_password"));

            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "select * from employee;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            List<Employee> employees = new ArrayList();
            while (rs.next()){                                              //MAKE THIS A LAMBDA??
                Employee employee = new Employee();

                employee.setId(rs.getInt("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setPassword(rs.getString("user_password"));

                employees.add(employee);
            }

            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Employee updateEmployeeRecord(Employee employee) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "update employee set first_name = ?, last_name = ?, user_password = ? where id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getPassword());
            ps.setInt(4, employee.getId());

            ps.executeUpdate();

            return employee;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean deleteEmployee(int id) {
        try {
            Connection conn = ConnectionUtil.createConnection();
            String sql = "delete from employee where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ps.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
