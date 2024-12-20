package com.location.voiture.controllers;

import com.location.voiture.models.Client;
import com.location.voiture.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder;

    public ClientController(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to Rent car application";
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients =  clientRepository.findAll();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("aucun client avec l'id: " + id));
        return new ResponseEntity<>(client, HttpStatus.OK);
    }
//
//    @PostMapping("/register")
//    public ResponseEntity<Client> createClient(@RequestBody Client client) {
//        client.setPassword(passwordEncoder.encode(client.getPassword()));
//        Client newClient = clientRepository.save(client);
//        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
//    }


}
