package com.location.voiture.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.location.voiture.models.OurUser;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String nom;
    private String prenom;
    private Boolean permisConduireValide;
    private String role;
    private String email;
    private String password;
    private String telephone;
    private String adresse;
    private OurUser ourUser;
    private List<OurUser> ourUsersList;
}
