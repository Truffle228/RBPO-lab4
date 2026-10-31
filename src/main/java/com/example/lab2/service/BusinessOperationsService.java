package com.example.lab2.service;

import com.example.lab2.entity.*;
import com.example.lab2.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BusinessOperationsService {
    private final ProductRepository productRepo;
    private final TaskRepository taskRepo;
    private final ReleaseRepository releaseRepo;
    private final CheckRepository checkRepo;
    private final UserRepository userRepo;

    public BusinessOperationsService(ProductRepository productRepo, TaskRepository taskRepo,
            ReleaseRepository releaseRepo, CheckRepository checkRepo, UserRepository userRepo) {
        this.productRepo = productRepo;
        this.taskRepo = taskRepo;
        this.releaseRepo = releaseRepo;
        this.checkRepo = checkRepo;
        this.userRepo = userRepo;
    }

    // 1. Выпустить релиз (Release + ReleaseCheck) — затрагивает 2 сущности
    @Transactional
    public Release publishRelease(Long productId, String versionNumber) {
        SoftwareProduct product = productRepo.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        // Проверяем что все задачи продукта завершены
        List<DevelopmentTask> tasks = taskRepo.findAll().stream()
            .filter(t -> t.getProduct().getId().equals(productId))
            .toList();
        boolean allDone = tasks.stream().allMatch(t -> "DONE".equals(t.getStatus()));
        if (!allDone) throw new RuntimeException("Not all tasks are completed");

        Release release = new Release(product, versionNumber, "RELEASED");
        release = releaseRepo.save(release);

        // Автоматически создаём проверку сборки
        ReleaseCheck buildCheck = new ReleaseCheck(release, "build", true, "PASSED");
        checkRepo.save(buildCheck);

        // Обновляем текущую версию продукта
        product.setCurrentVersion(release);
        productRepo.save(product);

        return release;
    }

    // 2. Назначить исполнителя на задачу (Task + User) — затрагивает 2 сущности
    @Transactional
    public DevelopmentTask assignTask(Long taskId, Long userId) {
        DevelopmentTask task = taskRepo.findById(taskId)
            .orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"NEW".equals(task.getStatus()) && !"IN_PROGRESS".equals(task.getStatus()))
            throw new RuntimeException("Can only assign tasks in NEW or IN_PROGRESS status");

        task.setAssignee(user);
        task.setStatus("IN_PROGRESS");
        return taskRepo.save(task);
    }

    // 3. Закрыть продукт (Product + все его Tasks) — затрагивает 2 сущности
    @Transactional
    public SoftwareProduct closeProduct(Long productId) {
        SoftwareProduct product = productRepo.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        List<DevelopmentTask> tasks = taskRepo.findAll().stream()
            .filter(t -> t.getProduct().getId().equals(productId))
            .toList();

        boolean hasOpen = tasks.stream().anyMatch(t -> !"DONE".equals(t.getStatus()));
        if (hasOpen) throw new RuntimeException("Cannot close product with open tasks");

        product.setName("[CLOSED] " + product.getName());
        return productRepo.save(product);
    }

    // 4. Провести все проверки релиза (Release + ReleaseCheck) — затрагивает 2 сущности
    @Transactional
    public Map<String, Object> runAllChecks(Long releaseId) {
        Release release = releaseRepo.findById(releaseId)
            .orElseThrow(() -> new RuntimeException("Release not found"));

        List<ReleaseCheck> checks = checkRepo.findAll().stream()
            .filter(c -> c.getRelease().getId().equals(releaseId))
            .toList();

        boolean allPassed = checks.stream()
            .filter(ReleaseCheck::isMandatory)
            .allMatch(c -> "PASSED".equals(c.getResult()));

        if (allPassed) {
            release.setStatus("APPROVED");
            releaseRepo.save(release);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("releaseId", releaseId);
        result.put("totalChecks", checks.size());
        result.put("allMandatoryPassed", allPassed);
        result.put("releaseStatus", release.getStatus());
        return result;
    }

    // 5. Переназначить ответственного (Product + User) — затрагивает 2 сущности
    @Transactional
    public SoftwareProduct reassignResponsible(Long productId, Long newUserId) {
        SoftwareProduct product = productRepo.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
        User newUser = userRepo.findById(newUserId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        product.setResponsible(newUser);
        return productRepo.save(product);
    }
}
