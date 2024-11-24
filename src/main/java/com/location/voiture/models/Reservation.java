package com.location.voiture.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "numero_reservation")
    private Long numeroReservation;

    @Column(name = "lieu_retrait")
    private String lieuRetrait;

    @Column(name = "date_depart")
    private LocalDate dateDepart;

    @Column(name = "date_retour")
    private LocalDate dateRetour;

    @Column(name = "assurance")
    private String assurance;

    @Column(name = "status")
    private String status;

    @Column(name = "is_approved")
    private boolean isApproved;
}
