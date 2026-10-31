package com.example.lab2.controller;
import com.example.lab2.dto.ReleaseDto;
import com.example.lab2.entity.Release;
import com.example.lab2.service.ReleaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/releases")
public class ReleaseController {
    private final ReleaseService service;
    public ReleaseController(ReleaseService service) { this.service = service; }
    @GetMapping public ResponseEntity<List<Release>> findAll() { return ResponseEntity.ok(service.findAll()); }
    @GetMapping("/{id}") public ResponseEntity<Release> findById(@PathVariable Long id) { Release r = service.findById(id); return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build(); }
    @PostMapping public ResponseEntity<Release> create(@RequestBody ReleaseDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PatchMapping("/{id}") public ResponseEntity<Release> update(@PathVariable Long id, @RequestBody Map<String, Object> patch) { Release r = service.update(id, patch); return r != null ? ResponseEntity.ok(r) : ResponseEntity.notFound().build(); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
