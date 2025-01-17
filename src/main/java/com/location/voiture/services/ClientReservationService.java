package com.location.voiture.services;

import com.location.voiture.dto.ClientReservationsDto;
import com.location.voiture.models.Reservation;
import com.location.voiture.repositories.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientReservationService {
    private final ReservationRepository reservationRepository;

    public ClientReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // Fetch all reservations for a client
    public List<ClientReservationsDto> getReservationsByClientId(Integer clientId) {
        List<Reservation> reservations = reservationRepository.findByClientId(clientId);
        return reservations.stream().map(reservation -> {
            ClientReservationsDto dto = new ClientReservationsDto();
            dto.setReservationId(reservation.getId());
            dto.setReservationNumber(reservation.getNumeroReservation());
            dto.setCarModel(reservation.getVoiture().getModele());
            dto.setReservationDate(reservation.getDateDepart());
            dto.setReturnDate(reservation.getDateRetour());
            dto.setTotalPrice(reservation.getTotalPrice());
            dto.setStatus(reservation.getStatus());
            return dto;
        }).collect(Collectors.toList());
    }

    // Cancel a reservation
    public void cancelReservation(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
        if (!"CANCELLED".equals(reservation.getStatus())) {
            reservation.setStatus("CANCELLED");
            reservationRepository.save(reservation);
        } else {
            throw new IllegalStateException("Reservation is already cancelled");
        }
    }
}