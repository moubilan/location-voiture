package com.location.voiture.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voitures")
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "marque")
    private String marque;

    @Column(name = "modele")
    private String modele;

    @Column(name = "type")
    private String type;

    @Column(name = "transmission")
    private String transmission;

    @Column(name = "nombre_siege")
    private Integer nombreSiege;

    @Column(name = "nombre_portes")
    private Integer nombrePortes;

    @Column(name = "prix_journalier")
    private Double prixJournalier;

    @Column(name = "is_rented")
    private Boolean isRented;

    @Column(name = "image")
    private String image;

    @JsonManagedReference("voiture")
    @OneToMany(mappedBy = "voiture", cascade = CascadeType.ALL)
    public List<Reservation> reservations;
}