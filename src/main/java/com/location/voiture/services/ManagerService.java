package com.location.voiture.services;

import com.location.voiture.models.Manager;
import com.location.voiture.repositories.ManagerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public Optional<Manager> getManagerById(Integer id) {
        return managerRepository.findById(id);
    }

    public Manager createManager(Manager manager) {
        return managerRepository.save(manager);
    }

    public Manager updateManager(Integer id, Manager managerDetails) {
        return managerRepository.findById(id).map(manager -> {
            manager.setEmail(managerDetails.getEmail());
            manager.setNom(managerDetails.getNom());
            manager.setPrenom(managerDetails.getPrenom());
            manager.setPassword(managerDetails.getPassword());
            manager.setRole(managerDetails.getRole());
            return managerRepository.save(manager);
        }).orElseThrow(() -> new EntityNotFoundException("Manager not found with id " + id));
    }

    public void deleteManager(Integer id) {
        managerRepository.deleteById(id);
    }
}

