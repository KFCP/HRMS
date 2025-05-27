package com.example.hrms.controller;

import com.example.hrms.model.Position;
import com.example.hrms.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/positions") // Base path for all position-related actions
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    // Display list of positions (query page)
    @GetMapping
    public String listPositions(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Position> positions;
        if (keyword != null && !keyword.trim().isEmpty()) {
            positions = positionService.findPositionsByName(keyword);
        } else {
            positions = positionService.getAllPositions();
        }
        model.addAttribute("positions", positions);
        model.addAttribute("keyword", keyword); // For repopulating search box
        return "position_query"; // View name for listing positions (e.g., /WEB-INF/jsp/position_query.jsp)
    }

    // Show form to add a new position
    @GetMapping("/add")
    public String showAddPositionForm(Model model) {
        model.addAttribute("position", new Position());
        // Assuming "position_form" is a generic form for both add and edit
        return "position_form"; // View name for the add/edit position form (e.g., /WEB-INF/jsp/position_form.jsp)
    }

    // Process adding a new position
    @PostMapping("/add")
    public String addPosition(@ModelAttribute("position") Position position, RedirectAttributes redirectAttributes) {
        // Basic validation could be added here using @Valid and BindingResult if desired
        if (position.getPositionName() == null || position.getPositionName().trim().isEmpty() || position.getLevel() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Position Name and Level are required.");
            // If using a dedicated form page that shows errors, redirect back to it
            return "redirect:/positions/add"; 
        }
        
        boolean success = positionService.addPosition(position);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Position added successfully!");
        } else {
            // More specific error message could come from the service or be set here
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to add position. It may already exist or input was invalid.");
        }
        return "redirect:/positions"; // Redirect to the list of positions
    }

    // Show form to edit an existing position
    @GetMapping("/edit/{id}")
    public String showEditPositionForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Position position = positionService.findPositionById(id);
        if (position == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Position not found with ID: " + id);
            return "redirect:/positions";
        }
        model.addAttribute("position", position);
        return "position_form"; // Reusing the same form for edit
    }

    // Process updating an existing position
    @PostMapping("/edit/{id}")
    public String updatePosition(@PathVariable("id") Long id, @ModelAttribute("position") Position position, RedirectAttributes redirectAttributes) {
        position.setId(id); // Ensure ID is set from path variable for the update
        
        if (position.getPositionName() == null || position.getPositionName().trim().isEmpty() || position.getLevel() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Position Name and Level are required.");
            // Redirect back to the edit form for this specific position
            return "redirect:/positions/edit/" + id; 
        }

        boolean success = positionService.updatePosition(position);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Position updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update position. Please check your input.");
        }
        return "redirect:/positions"; // Redirect to the list of positions
    }

    // Handle deletion of a position
    @GetMapping("/delete/{id}") // Using GET for simplicity, POST with a form is safer for delete operations
    public String deletePosition(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        // Add confirmation logic in the view before calling this
        boolean success = positionService.deletePosition(id);
        if (success) {
            redirectAttributes.addFlashAttribute("successMessage", "Position deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete position. It might be in use or not found.");
        }
        return "redirect:/positions";
    }
}
