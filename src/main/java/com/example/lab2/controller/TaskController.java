package com.example.lab2.controller;
import com.example.lab2.dto.TaskDto;
import com.example.lab2.entity.DevelopmentTask;
import com.example.lab2.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/development-tasks")
public class TaskController {
    private final TaskService service;
    public TaskController(TaskService service) { this.service = service; }
    @GetMapping public ResponseEntity<List<DevelopmentTask>> findAll() { return ResponseEntity.ok(service.findAll()); }
    @GetMapping("/{id}") public ResponseEntity<DevelopmentTask> findById(@PathVariable Long id) { DevelopmentTask t = service.findById(id); return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build(); }
    @PostMapping public ResponseEntity<DevelopmentTask> create(@RequestBody TaskDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PatchMapping("/{id}") public ResponseEntity<DevelopmentTask> update(@PathVariable Long id, @RequestBody Map<String, Object> patch) { DevelopmentTask t = service.update(id, patch); return t != null ? ResponseEntity.ok(t) : ResponseEntity.notFound().build(); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
