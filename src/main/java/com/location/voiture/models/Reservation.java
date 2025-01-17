package com.location.voiture.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(name = "kilometrage")
    private String kilometrage;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "status")
    private String status;

    @Column(name = "is_approved")
    private boolean isApproved;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @JsonBackReference("client")
    @ManyToOne
    @JoinColumn(name = "client_id")
    public Client client;

    @JsonBackReference("voiture")
    @ManyToOne
    @JoinColumn(name = "voiture_id")
    public Voiture voiture;
}
