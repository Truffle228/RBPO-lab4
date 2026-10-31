package com.example.lab2.controller;

import com.example.lab2.dto.UserDto;
import com.example.lab2.dto.UserResponse;
import com.example.lab2.entity.User;
import com.example.lab2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> users = service.findAll().stream()
            .map(UserResponse::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User u = service.findById(id);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new UserResponse(u));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserDto dto) {
        User u = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponse(u));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody Map<String, Object> patch) {
        User u = service.update(id, patch);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new UserResponse(u));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
