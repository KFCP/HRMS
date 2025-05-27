package com.example.hrms.mapper;

import com.example.hrms.model.Employee;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface EmployeeMapper {
    int insertEmployee(Employee employee);

    Employee findEmployeeById(@Param("id") Long id);

    List<Employee> findAllEmployees();

    List<Employee> findEmployeesByKeyword(@Param("keyword") String keyword);

    int updateEmployee(Employee employee);

    int deleteEmployee(@Param("id") Long id);
}
