package com.location.voiture.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClientReservationsDto {
    private Integer reservationId;
    private Long reservationNumber;
    private String carModel;
    private LocalDate reservationDate;
    private LocalDate returnDate;
    private Double totalPrice;
    private String status;
    private LocalDateTime createdAt;

}