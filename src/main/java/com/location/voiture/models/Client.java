package com.location.voiture.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("CLIENT")
public class Client extends OurUser{

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "permis_conduire_valide")
    private boolean permisConduireValide;

    @JsonManagedReference("client")
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    public List<Reservation> reservations;
}
