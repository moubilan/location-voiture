package com.location.voiture.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
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
    private Integer prixJournalier;

    @Column(name = "is_rented")
    private Boolean isRented;

    @OneToMany(mappedBy = "voiture", cascade = CascadeType.ALL)
    public List<Reservation> reservations;
}