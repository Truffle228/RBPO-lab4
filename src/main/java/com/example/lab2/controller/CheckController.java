package com.example.lab2.controller;
import com.example.lab2.dto.CheckDto;
import com.example.lab2.entity.ReleaseCheck;
import com.example.lab2.service.CheckService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/release-checks")
public class CheckController {
    private final CheckService service;
    public CheckController(CheckService service) { this.service = service; }
    @GetMapping public ResponseEntity<List<ReleaseCheck>> findAll() { return ResponseEntity.ok(service.findAll()); }
    @GetMapping("/{id}") public ResponseEntity<ReleaseCheck> findById(@PathVariable Long id) { ReleaseCheck c = service.findById(id); return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build(); }
    @PostMapping public ResponseEntity<ReleaseCheck> create(@RequestBody CheckDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PatchMapping("/{id}") public ResponseEntity<ReleaseCheck> update(@PathVariable Long id, @RequestBody Map<String, Object> patch) { ReleaseCheck c = service.update(id, patch); return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build(); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
