package com.example.hrms.service.impl;

import com.example.hrms.model.Employee;
import com.example.hrms.model.Position; // For potential validation
import com.example.hrms.mapper.EmployeeMapper;
import com.example.hrms.mapper.PositionMapper; // For validating positionId
import com.example.hrms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final PositionMapper positionMapper; // Injected for position validation

    @Autowired
    public EmployeeServiceImpl(EmployeeMapper employeeMapper, PositionMapper positionMapper) {
        this.employeeMapper = employeeMapper;
        this.positionMapper = positionMapper;
    }

    @Override
    @Transactional
    public boolean addEmployee(Employee employee) {
        if (employee == null || employee.getName() == null || employee.getName().trim().isEmpty() || employee.getPositionId() == null) {
            return false; // Basic validation
        }
        // Validate that the positionId exists
        Position position = positionMapper.findPositionById(employee.getPositionId());
        if (position == null) {
            return false; // Position does not exist
        }
        return employeeMapper.insertEmployee(employee) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeById(Long id) {
        return employeeMapper.findEmployeeById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeMapper.findAllEmployees();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findEmployeesByKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllEmployees(); // Or return an empty list
        }
        return employeeMapper.findEmployeesByKeyword(keyword);
    }

    @Override
    @Transactional
    public boolean updateEmployee(Employee employee) {
        if (employee == null || employee.getId() == null || employee.getName() == null || employee.getName().trim().isEmpty() || employee.getPositionId() == null) {
            return false; // Basic validation
        }
        // Validate that the positionId exists
        Position position = positionMapper.findPositionById(employee.getPositionId());
        if (position == null) {
            return false; // Position does not exist
        }
        // Ensure the employee being updated actually exists
        if (employeeMapper.findEmployeeById(employee.getId()) == null) {
            return false; // Employee not found
        }
        return employeeMapper.updateEmployee(employee) > 0;
    }

    @Override
    @Transactional
    public boolean deleteEmployee(Long id) {
        if (id == null) {
            return false;
        }
        return employeeMapper.deleteEmployee(id) > 0;
    }
}
