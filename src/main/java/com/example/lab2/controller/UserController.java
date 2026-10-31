package com.example.lab2.controller;
import com.example.lab2.dto.UserDto;
import com.example.lab2.entity.User;
import com.example.lab2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService service;
    public UserController(UserService service) { this.service = service; }
    @GetMapping public ResponseEntity<List<User>> findAll() { return ResponseEntity.ok(service.findAll()); }
    @GetMapping("/{id}") public ResponseEntity<User> findById(@PathVariable Long id) { User u = service.findById(id); return u != null ? ResponseEntity.ok(u) : ResponseEntity.notFound().build(); }
    @PostMapping public ResponseEntity<User> create(@RequestBody UserDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PatchMapping("/{id}") public ResponseEntity<User> update(@PathVariable Long id, @RequestBody Map<String, Object> patch) { User u = service.update(id, patch); return u != null ? ResponseEntity.ok(u) : ResponseEntity.notFound().build(); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
