package com.emp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.emp.model.Emp;
import com.emp.service.EmpService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmpController {

    @Autowired
    private EmpService service;

    // Load login page
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    // Load registration page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("emp", new Emp());
        return "register";
    }

    // Save registration data
    @PostMapping("/register")
    public String registerEmp(@ModelAttribute Emp e) {
        service.register(e);
        return "redirect:/";
    }

    // Login logic
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        Emp e = service.login(email, password);

        if (e != null) {
            session.setAttribute("emplogged", e);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    // Dashboard page
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        Emp e = (Emp) session.getAttribute("emplogged");

        if (e == null) {
            return "redirect:/";
        }

        model.addAttribute("emp", e);
        return "dashboard";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}