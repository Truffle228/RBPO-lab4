package com.example.lab2.service;

import com.example.lab2.dto.CheckDto;
import com.example.lab2.entity.ReleaseCheck;
import com.example.lab2.repository.CheckRepository;
import com.example.lab2.repository.ReleaseRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CheckService {
    private final CheckRepository checkRepo;
    private final ReleaseRepository releaseRepo;
    public CheckService(CheckRepository checkRepo, ReleaseRepository releaseRepo) {
        this.checkRepo = checkRepo;
        this.releaseRepo = releaseRepo;
    }

    public List<ReleaseCheck> findAll() { return checkRepo.findAll(); }
    public ReleaseCheck findById(Long id) { return checkRepo.findById(id).orElse(null); }

    public ReleaseCheck create(CheckDto dto) {
        ReleaseCheck c = new ReleaseCheck();
        if (dto.getReleaseId() != null)
            c.setRelease(releaseRepo.findById(dto.getReleaseId()).orElse(null));
        c.setCheckName(dto.getCheckName());
        c.setMandatory(dto.isMandatory());
        c.setResult(dto.getResult());
        return checkRepo.save(c);
    }

    public ReleaseCheck update(Long id, Map<String, Object> patch) {
        ReleaseCheck c = checkRepo.findById(id).orElse(null);
        if (c == null) return null;
        if (patch.containsKey("checkName")) c.setCheckName((String) patch.get("checkName"));
        if (patch.containsKey("mandatory")) c.setMandatory((Boolean) patch.get("mandatory"));
        if (patch.containsKey("result")) c.setResult((String) patch.get("result"));
        if (patch.containsKey("releaseId")) {
            Long rid = ((Number) patch.get("releaseId")).longValue();
            c.setRelease(releaseRepo.findById(rid).orElse(null));
        }
        return checkRepo.save(c);
    }

    public boolean delete(Long id) {
        if (!checkRepo.existsById(id)) return false;
        checkRepo.deleteById(id);
        return true;
    }
}
