package com.example.hrms.controller;

import com.example.hrms.model.Employee;
import com.example.hrms.model.Position;
import com.example.hrms.service.EmployeeService;
import com.example.hrms.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees") // Base path for employee-related actions
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PositionService positionService; // To provide list of positions for forms

    @Autowired
    public EmployeeController(EmployeeService employeeService, PositionService positionService) {
        this.employeeService = employeeService;
        this.positionService = positionService;
    }

    // Common method to load positions into the model for forms
    private void loadPositions(Model model) {
        List<Position> positions = positionService.getAllPositions();
        model.addAttribute("allPositions", positions);
    }

    // Display list of employees (query page)
    @GetMapping
    public String listEmployees(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Employee> employees;
        if (keyword != null && !keyword.trim().isEmpty()) {
            employees = employeeService.findEmployeesByKeyword(keyword);
        } else {
            employees = employeeService.getAllEmployees();
        }
        model.addAttribute("employees", employees);
        model.addAttribute("keyword", keyword);
        return "employee_query"; // View name: /WEB-INF/jsp/employee_query.jsp
    }

    // Show form to add a new employee
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        loadPositions(model); // Load positions for dropdown
        return "employee_form"; // View name: /WEB-INF/jsp/employee_form.jsp
    }

    // Process adding a new employee
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute("employee") Employee employee, RedirectAttributes redirectAttributes) {
        // Basic validation (can be enhanced with @Valid)
        if (employee.getName() == null || employee.getName().trim().isEmpty() || 
            employee.getPositionId() == null || employee.getAge() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Name, Age, and Position are required.");
            // It's tricky to redirect back to form with @ModelAttribute state without more setup
            // Usually, you'd return "employee_form" directly and handle errors in the model
            // For simplicity with RedirectAttributes, redirecting to GET /add
            redirectAttributes.addFlashAttribute("employee", employee); // Try to repopulate form
            return "redirect:/employees/add";
        }

        boolean success = employeeService.addEmployee(employee);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Employee added successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add employee. Please check input or if position is valid.");
            redirectAttributes.addFlashAttribute("employee", employee);
             return "redirect:/employees/add";
        }
        return "redirect:/employees";
    }

    // Show form to edit an existing employee
    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Employee employee = employeeService.findEmployeeById(id);
        if (employee == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Employee not found with ID: " + id);
            return "redirect:/employees";
        }
        model.addAttribute("employee", employee);
        loadPositions(model); // Load positions for dropdown
        return "employee_form"; // Reusing the same form
    }

    // Process updating an existing employee
    @PostMapping("/edit/{id}")
    public String updateEmployee(@PathVariable("id") Long id, @ModelAttribute("employee") Employee employee, RedirectAttributes redirectAttributes) {
        employee.setId(id); // Ensure ID is set from path variable

        if (employee.getName() == null || employee.getName().trim().isEmpty() || 
            employee.getPositionId() == null || employee.getAge() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Name, Age, and Position are required.");
            redirectAttributes.addFlashAttribute("employee", employee);
            return "redirect:/employees/edit/" + id;
        }
        
        boolean success = employeeService.updateEmployee(employee);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Employee updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update employee. Ensure position is valid.");
            redirectAttributes.addFlashAttribute("employee", employee);
            return "redirect:/employees/edit/" + id;
        }
        return "redirect:/employees";
    }

    // Handle deletion of an employee
    @GetMapping("/delete/{id}") // Using GET for simplicity, POST is safer
    public String deleteEmployee(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean success = employeeService.deleteEmployee(id);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete employee.");
        }
        return "redirect:/employees";
    }
}
