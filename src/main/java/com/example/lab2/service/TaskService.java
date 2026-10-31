package com.example.lab2.service;

import com.example.lab2.dto.TaskDto;
import com.example.lab2.entity.DevelopmentTask;
import com.example.lab2.repository.TaskRepository;
import com.example.lab2.repository.ProductRepository;
import com.example.lab2.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TaskService {
    private final TaskRepository taskRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    public TaskService(TaskRepository taskRepo, ProductRepository productRepo, UserRepository userRepo) {
        this.taskRepo = taskRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public List<DevelopmentTask> findAll() { return taskRepo.findAll(); }
    public DevelopmentTask findById(Long id) { return taskRepo.findById(id).orElse(null); }

    public DevelopmentTask create(TaskDto dto) {
        DevelopmentTask t = new DevelopmentTask();
        if (dto.getProductId() != null)
            t.setProduct(productRepo.findById(dto.getProductId()).orElse(null));
        if (dto.getAssigneeId() != null)
            t.setAssignee(userRepo.findById(dto.getAssigneeId()).orElse(null));
        t.setVersion(dto.getVersion());
        t.setStatus(dto.getStatus());
        return taskRepo.save(t);
    }

    public DevelopmentTask update(Long id, Map<String, Object> patch) {
        DevelopmentTask t = taskRepo.findById(id).orElse(null);
        if (t == null) return null;
        if (patch.containsKey("version")) t.setVersion((String) patch.get("version"));
        if (patch.containsKey("status")) t.setStatus((String) patch.get("status"));
        if (patch.containsKey("productId")) {
            Long pid = ((Number) patch.get("productId")).longValue();
            t.setProduct(productRepo.findById(pid).orElse(null));
        }
        if (patch.containsKey("assigneeId")) {
            Long aid = ((Number) patch.get("assigneeId")).longValue();
            t.setAssignee(userRepo.findById(aid).orElse(null));
        }
        return taskRepo.save(t);
    }

    public boolean delete(Long id) {
        if (!taskRepo.existsById(id)) return false;
        taskRepo.deleteById(id);
        return true;
    }
}
