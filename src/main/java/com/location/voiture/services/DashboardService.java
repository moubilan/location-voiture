package com.location.voiture.services;

import com.location.voiture.dto.ClientReservationsDto;
import com.location.voiture.dto.DashboardMetricsDto;
import com.location.voiture.models.Reservation;
import com.location.voiture.repositories.ReservationRepository;
import com.location.voiture.repositories.VoitureRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    private final VoitureRepository voitureRepository;
    private final ReservationRepository reservationRepository;

    public DashboardService(VoitureRepository voitureRepository, ReservationRepository reservationRepository) {
        this.voitureRepository = voitureRepository;
        this.reservationRepository = reservationRepository;
    }

    public DashboardMetricsDto getDashboardMetrics() {
        DashboardMetricsDto metrics = new DashboardMetricsDto();

        // Fetch and calculate metrics
        metrics.setTotalCars(voitureRepository.count());
        metrics.setTotalReservations(reservationRepository.count());
        metrics.setTotalRevenue(reservationRepository.calculateTotalRevenue());
        metrics.setReservationsToApprove(reservationRepository.countPendingReservations());
        //metrics.setReservations(getLatestReservations());
        metrics.setLatestReservations(getLatestReservations());
        return metrics;
    }

    public List<ClientReservationsDto> getLatestReservations() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Reservation> reservations = reservationRepository.findLatestReservations(pageable);

        return reservations.stream().map(reservation -> {
            ClientReservationsDto dto = new ClientReservationsDto();
            dto.setReservationId(reservation.getId());
            dto.setReservationNumber(reservation.getNumeroReservation());
            dto.setCarModel(reservation.getVoiture().getModele());
            dto.setReservationDate(reservation.getDateDepart());
            dto.setReturnDate(reservation.getDateRetour());
            dto.setTotalPrice(reservation.getTotalPrice());
            dto.setStatus(reservation.getStatus());
            dto.setCreatedAt(reservation.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());
    }



//    public List<Reservation> getLatestReservations() {
//        List<Reservation> reservations = reservationRepository.findAll();
//
//        return reservations.stream()
//                .filter(reservation -> reservation.getCreatedAt() != null) // Skip reservations where createdAt is null
//                .sorted((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt())) // Sort by createdAt in descending order
//                .limit(5) // Limit the result to 5 reservations
//                .collect(Collectors.toList()); // Collect the results into a List
//    }

}

