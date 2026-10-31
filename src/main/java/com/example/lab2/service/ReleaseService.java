package com.example.lab2.service;

import com.example.lab2.dto.ReleaseDto;
import com.example.lab2.entity.Release;
import com.example.lab2.repository.ReleaseRepository;
import com.example.lab2.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ReleaseService {
    private final ReleaseRepository releaseRepo;
    private final ProductRepository productRepo;
    public ReleaseService(ReleaseRepository releaseRepo, ProductRepository productRepo) {
        this.releaseRepo = releaseRepo;
        this.productRepo = productRepo;
    }

    public List<Release> findAll() { return releaseRepo.findAll(); }
    public Release findById(Long id) { return releaseRepo.findById(id).orElse(null); }

    public Release create(ReleaseDto dto) {
        Release r = new Release();
        if (dto.getProductId() != null)
            r.setProduct(productRepo.findById(dto.getProductId()).orElse(null));
        r.setNumber(dto.getNumber());
        r.setStatus(dto.getStatus());
        return releaseRepo.save(r);
    }

    public Release update(Long id, Map<String, Object> patch) {
        Release r = releaseRepo.findById(id).orElse(null);
        if (r == null) return null;
        if (patch.containsKey("number")) r.setNumber((String) patch.get("number"));
        if (patch.containsKey("status")) r.setStatus((String) patch.get("status"));
        if (patch.containsKey("productId")) {
            Long pid = ((Number) patch.get("productId")).longValue();
            r.setProduct(productRepo.findById(pid).orElse(null));
        }
        return releaseRepo.save(r);
    }

    public boolean delete(Long id) {
        if (!releaseRepo.existsById(id)) return false;
        releaseRepo.deleteById(id);
        return true;
    }
}
