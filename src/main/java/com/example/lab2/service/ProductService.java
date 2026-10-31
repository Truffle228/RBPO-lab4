package com.example.lab2.service;

import com.example.lab2.dto.ProductDto;
import com.example.lab2.entity.SoftwareProduct;
import com.example.lab2.repository.ProductRepository;
import com.example.lab2.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    public ProductService(ProductRepository productRepo, UserRepository userRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public List<SoftwareProduct> findAll() { return productRepo.findAll(); }
    public SoftwareProduct findById(Long id) { return productRepo.findById(id).orElse(null); }

    public SoftwareProduct create(ProductDto dto) {
        SoftwareProduct p = new SoftwareProduct();
        p.setName(dto.getName());
        if (dto.getResponsibleId() != null)
            p.setResponsible(userRepo.findById(dto.getResponsibleId()).orElse(null));
        return productRepo.save(p);
    }

    public SoftwareProduct update(Long id, Map<String, Object> patch) {
        SoftwareProduct p = productRepo.findById(id).orElse(null);
        if (p == null) return null;
        if (patch.containsKey("name")) p.setName((String) patch.get("name"));
        if (patch.containsKey("responsibleId")) {
            Long rid = ((Number) patch.get("responsibleId")).longValue();
            p.setResponsible(userRepo.findById(rid).orElse(null));
        }
        return productRepo.save(p);
    }

    public boolean delete(Long id) {
        if (!productRepo.existsById(id)) return false;
        productRepo.deleteById(id);
        return true;
    }
}
