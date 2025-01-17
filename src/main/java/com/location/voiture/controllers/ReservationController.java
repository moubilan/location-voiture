package com.location.voiture.controllers;

import com.location.voiture.models.Reservation;
import com.location.voiture.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/public/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    // Get all reservations
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return ResponseEntity.ok(reservations);
    }

    // Get reservation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Integer id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return reservation.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Create a new reservation
    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation savedReservation = reservationRepository.save(reservation);
        return ResponseEntity.status(201).body(savedReservation);
    }

    // Update a reservation
    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable Integer id,
            @RequestBody Reservation reservationDetails) {
        return reservationRepository.findById(id).map(reservation -> {
            reservation.setNumeroReservation(reservationDetails.getNumeroReservation());
            reservation.setLieuRetrait(reservationDetails.getLieuRetrait());
            reservation.setDateDepart(reservationDetails.getDateDepart());
            reservation.setDateRetour(reservationDetails.getDateRetour());
            reservation.setAssurance(reservationDetails.getAssurance());
            reservation.setStatus(reservationDetails.getStatus());
            reservation.setApproved(reservationDetails.isApproved());
            reservation.setClient(reservationDetails.getClient());
            reservation.setVoiture(reservationDetails.getVoiture());
            Reservation updatedReservation = reservationRepository.save(reservation);
            return ResponseEntity.ok(updatedReservation);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete a reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Integer id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}