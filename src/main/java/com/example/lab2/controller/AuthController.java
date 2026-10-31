package com.example.lab2.controller;

import com.example.lab2.dto.RegisterRequest;
import com.example.lab2.entity.User;
import com.example.lab2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(Map.of(
                "message", "User registered successfully",
                "username", user.getUsername()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/csrf")
    public Map<String, String> getCsrfToken(HttpServletRequest request) {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        return Map.of(
            "token", csrfToken.getToken(),
            "header", csrfToken.getHeaderName()
        );
    }
}
