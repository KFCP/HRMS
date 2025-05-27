package com.example.hrms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import javax.servlet.http.HttpSession; // For checking session if not using Spring Security yet

@Controller
public class HomeController {

    @GetMapping("/") // Maps requests to the root path to this method
    public String home() {
        // Later, with Spring Security, we can get Principal object to check authentication
        // For now, assumes if they hit /, they go to index or login.
        // If Spring Security is set up, it might intercept and redirect to login if not authenticated.
        return "index"; // Returns the view name "index" (maps to /WEB-INF/jsp/index.jsp)
    }

    @GetMapping("/index") // Explicit mapping for /index
    public String indexPage() {
        return "index";
    }
}
