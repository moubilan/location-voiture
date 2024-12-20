package com.location.voiture.controllers;

import com.location.voiture.repositories.ManagerRepository;

public class ManagerController {

    private final ManagerRepository managerRepository;

    ManagerController(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }


}
