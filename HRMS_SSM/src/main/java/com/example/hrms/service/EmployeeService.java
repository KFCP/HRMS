package com.example.hrms.service;

import com.example.hrms.model.Employee;
import java.util.List;

public interface EmployeeService {
    boolean addEmployee(Employee employee);
    Employee findEmployeeById(Long id);
    List<Employee> getAllEmployees();
    List<Employee> findEmployeesByKeyword(String keyword);
    boolean updateEmployee(Employee employee); // Covers "Personnel Adjustment"
    boolean deleteEmployee(Long id);
    
    // Consider methods for "Position Assignment" if it's more complex than setting positionId on Employee
    // For now, handled by addEmployee/updateEmployee.

    // Consider methods for "Employee salary information management" 
    // if it's more than just a field on Employee.
    // For now, salary is part of Employee object.
}
