package com.example.hrms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.servlet.ModelAndView;
// import javax.servlet.http.HttpServletRequest; // For manual login if not using Spring Security

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        // This just shows the login page.
        // Spring Security will typically provide its own /login POST endpoint or integrate with this.
        return "user_login"; // Returns the view name "user_login" (maps to /WEB-INF/jsp/user_login.jsp)
    }

    // Logout mapping - Spring Security will also handle this more robustly.
    // This is a placeholder if we need a GET link for logout before full security setup.
    @GetMapping("/logout")
    public String logout() {
        // In a Spring Security setup, this would be handled by its logout filter.
        // Redirecting to login page after "logout".
        return "redirect:/login?logout"; 
    }
}
