package com.location.voiture.controllers;

import com.location.voiture.dto.ClientReservationsDto;
import com.location.voiture.services.ClientReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/api/client-reservations")
public class ClientReservationController {
    private final ClientReservationService clientReservationService;

    public ClientReservationController(ClientReservationService clientReservationService) {
        this.clientReservationService = clientReservationService;
    }

    // Endpoint to get reservations by client ID
    @GetMapping("/{clientId}")
    public ResponseEntity<List<ClientReservationsDto>> getClientReservations(@PathVariable Integer clientId) {
        List<ClientReservationsDto> reservations = clientReservationService.getReservationsByClientId(clientId);
        return ResponseEntity.ok(reservations);
    }

    // Endpoint to cancel a reservation
    @PostMapping("/cancel/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Integer reservationId) {
        clientReservationService.cancelReservation(reservationId);
        return ResponseEntity.ok().build();
    }
}

