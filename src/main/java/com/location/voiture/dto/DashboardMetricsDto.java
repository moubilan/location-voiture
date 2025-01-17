package com.location.voiture.dto;

import com.location.voiture.models.Reservation;
import lombok.Data;

import java.util.List;

@Data
public class DashboardMetricsDto {
    private long totalCars;
    private long totalReservations;
    private double totalRevenue;
    private long reservationsToApprove;
    //private List<Reservation> reservations;
    private List<ClientReservationsDto> latestReservations;
}
