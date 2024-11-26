package com.location.voiture.controllers;

import com.location.voiture.models.Client;
import com.location.voiture.repositories.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("aucun client avec l'id: " + id));
        return new ResponseEntity<>(client, HttpStatus.OK);
    }


}
