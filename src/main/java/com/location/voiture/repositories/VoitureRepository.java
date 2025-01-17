package com.location.voiture.repositories;

import com.location.voiture.models.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoitureRepository extends JpaRepository<Voiture, Integer> {
    long count();
}
