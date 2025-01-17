package com.location.voiture.controllers;

import com.location.voiture.models.Manager;
import com.location.voiture.services.ManagerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/api")
public class ManagerController {

    private final ManagerService managerService;

    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    // Get all managers
    @GetMapping("/managers")
    public List<Manager> getAllManagers() {
        return managerService.getAllManagers();
    }

    // Get manager by ID
    @GetMapping("/managers/{id}")
    public ResponseEntity<Manager> getManagerById(@PathVariable Integer id) {
        return managerService.getManagerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new manager
    @PostMapping("/managers")
    public ResponseEntity<Manager> createManager(@RequestBody Manager manager) {
        Manager createdManager = managerService.createManager(manager);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdManager);
    }

    // Update an existing manager
    @PutMapping("/managers/{id}")
    public ResponseEntity<Manager> updateManager(@PathVariable Integer id, @RequestBody Manager managerDetails) {
        try {
            Manager updatedManager = managerService.updateManager(id, managerDetails);
            return ResponseEntity.ok(updatedManager);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a manager
    @DeleteMapping("/managers/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Integer id) {
        try {
            managerService.deleteManager(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

