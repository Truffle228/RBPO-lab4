package com.example.lab2.controller;

import com.example.lab2.entity.*;
import com.example.lab2.service.BusinessOperationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/business")
public class BusinessOperationsController {
    private final BusinessOperationsService service;
    public BusinessOperationsController(BusinessOperationsService service) { this.service = service; }

    // 1. Выпустить релиз (Release + ReleaseCheck)
    @PostMapping("/release/publish")
    public ResponseEntity<Release> publishRelease(@RequestParam Long productId, @RequestParam String version) {
        return ResponseEntity.ok(service.publishRelease(productId, version));
    }

    // 2. Назначить исполнителя (Task + User)
    @PostMapping("/task/assign")
    public ResponseEntity<DevelopmentTask> assignTask(@RequestParam Long taskId, @RequestParam Long userId) {
        return ResponseEntity.ok(service.assignTask(taskId, userId));
    }

    // 3. Закрыть продукт (Product + Tasks)
    @PostMapping("/product/close")
    public ResponseEntity<SoftwareProduct> closeProduct(@RequestParam Long productId) {
        return ResponseEntity.ok(service.closeProduct(productId));
    }

    // 4. Провести все проверки релиза (Release + ReleaseCheck)
    @PostMapping("/release/check-all")
    public ResponseEntity<Map<String, Object>> runAllChecks(@RequestParam Long releaseId) {
        return ResponseEntity.ok(service.runAllChecks(releaseId));
    }

    // 5. Переназначить ответственного (Product + User)
    @PostMapping("/product/reassign")
    public ResponseEntity<SoftwareProduct> reassignResponsible(@RequestParam Long productId, @RequestParam Long userId) {
        return ResponseEntity.ok(service.reassignResponsible(productId, userId));
    }
}
