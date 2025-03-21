package com.ms.homeloanapplication.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/adminDashboard")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminDashboard() {
        return "adminDashboard";
    }

    @GetMapping("/clientDashboard")
    //@PreAuthorize("hasRole('ROLE_CLIENT')")
    public String clientDashboard() {
        return "clientDashboard";
    }
}
