package com.example.lab2.controller;
import com.example.lab2.dto.ProductDto;
import com.example.lab2.entity.SoftwareProduct;
import com.example.lab2.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/software-products")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }
    @GetMapping public ResponseEntity<List<SoftwareProduct>> findAll() { return ResponseEntity.ok(service.findAll()); }
    @GetMapping("/{id}") public ResponseEntity<SoftwareProduct> findById(@PathVariable Long id) { SoftwareProduct p = service.findById(id); return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build(); }
    @PostMapping public ResponseEntity<SoftwareProduct> create(@RequestBody ProductDto dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PatchMapping("/{id}") public ResponseEntity<SoftwareProduct> update(@PathVariable Long id, @RequestBody Map<String, Object> patch) { SoftwareProduct p = service.update(id, patch); return p != null ? ResponseEntity.ok(p) : ResponseEntity.notFound().build(); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { return service.delete(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); }
}
