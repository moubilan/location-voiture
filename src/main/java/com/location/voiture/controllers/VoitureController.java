package com.location.voiture.controllers;

import com.location.voiture.models.Voiture;
import com.location.voiture.repositories.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("public/api/voitures")
@CrossOrigin(origins = "*")
public class VoitureController {

    @Autowired
    private VoitureRepository voitureRepository;

    @GetMapping
    public ResponseEntity<List<Voiture>> getAllVoitures() {
        List<Voiture> voitures = voitureRepository.findAll();
        return ResponseEntity.ok(voitures);
    }

    // Get car by ID
    @GetMapping("/{id}")
    public ResponseEntity<Voiture> getVoitureById(@PathVariable Integer id) {
        return voitureRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Voiture> createVoiture(@RequestBody Voiture voiture) {
        Voiture savedVoiture = voitureRepository.save(voiture);
        return ResponseEntity.status(201).body(savedVoiture);
    }

    // Update a car
    @PutMapping("/{id}")
    public ResponseEntity<Voiture> updateVoiture(@PathVariable Integer id, @RequestBody Voiture voitureDetails) {
        return voitureRepository.findById(id).map(voiture -> {
            voiture.setMarque(voitureDetails.getMarque());
            voiture.setModele(voitureDetails.getModele());
            voiture.setType(voitureDetails.getType());
            voiture.setTransmission(voitureDetails.getTransmission());
            voiture.setNombreSiege(voitureDetails.getNombreSiege());
            voiture.setNombrePortes(voitureDetails.getNombrePortes());
            voiture.setPrixJournalier(voitureDetails.getPrixJournalier());
            voiture.setIsRented(voitureDetails.getIsRented());
            return ResponseEntity.ok(voitureRepository.save(voiture));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete a car
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoiture(@PathVariable Integer id) {
        if (voitureRepository.existsById(id)) {
            voitureRepository.deleteById(id);
            return ResponseEntity.noContent().build(); // Return 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }
    }
}
