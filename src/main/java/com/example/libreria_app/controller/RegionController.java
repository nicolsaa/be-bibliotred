package com.example.libreria_app.controller;

import java.util.List;


import org.springframework.web.bind.annotation.*;

import com.example.libreria_app.model.Region;
import com.example.libreria_app.dto.RegionCreateRequest;
import com.example.libreria_app.dto.RegionUpdateRequest;
import com.example.libreria_app.service.RegionService;

@RestController
@RequestMapping("/regiones")
public class RegionController {
    private final RegionService regionService;


    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public List<Region> getAllRegions() {
        return regionService.getAllRegions();
    }

    @GetMapping("/{id}")
    public Region getRegion(@PathVariable Long id) {
        return regionService.getRegionById(id);
    }

    @PostMapping
    public Region createRegion(@RequestBody RegionCreateRequest request) {
        return regionService.createRegion(request);
    }

    @PutMapping("/{id}")
    public Region updateRegion(@PathVariable Long id, @RequestBody RegionUpdateRequest request) {
        return regionService.updateRegion(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteRegion(@PathVariable Long id) {
        regionService.deleteRegion(id);
    }
}
