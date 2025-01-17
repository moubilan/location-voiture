package com.location.voiture.repositories;

import com.location.voiture.models.OurUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<OurUser, Integer> {
    Optional<OurUser> findByEmail(String email);
}
