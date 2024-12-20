package com.location.voiture.controllers;

import com.location.voiture.models.AppUser;
import com.location.voiture.models.Client;
import com.location.voiture.repositories.AppUserRepository;
import com.location.voiture.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AppUserController {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    public AppUserController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to Rent car application";
    }

    @GetMapping("api/users")
    public ResponseEntity<List<AppUser>> getAllClients() {
        List<AppUser> users =  appUserRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("api/register")
    public ResponseEntity<AppUser> createUser(@RequestBody AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser newUser = appUserRepository.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
