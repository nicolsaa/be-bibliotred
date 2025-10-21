package com.example.libreria_app.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.libreria_app.model.Region;
import com.example.libreria_app.dto.RegionCreateRequest;
import com.example.libreria_app.dto.RegionUpdateRequest;
import com.example.libreria_app.repository.RegionRepository;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    public Region getRegionById(Long id) {
        return regionRepository.findById(id).orElseThrow(() -> new RuntimeException("Region no encontrado"));
    }

    public Region createRegion(RegionCreateRequest request) {
        if (request == null || request.getNombre_region() == null || request.getNombre_region().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre de region es obligatorio");
        }
        Region r = new Region();
        r.setNombre(request.getNombre_region().trim());
        return regionRepository.save(r);
    }

    public Region updateRegion(Long id, RegionUpdateRequest request) {
        Region existing = getRegionById(id);
        if (request != null) {
            String newName = request.getNombre_region();
            if (newName != null && !newName.trim().isEmpty()) {
            existing.setNombre(newName.trim());
            }
        }
        return regionRepository.save(existing);
    }

    public void deleteRegion(Long id) {
        if (!regionRepository.existsById(id)) {
            throw new RuntimeException("Region no encontrado");
        }
        regionRepository.deleteById(id);
    }
}
