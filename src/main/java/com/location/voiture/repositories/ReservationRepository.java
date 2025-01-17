package com.location.voiture.repositories;

import com.location.voiture.models.Reservation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    long count();

    //@Query("SELECT SUM(r.totalPrice) FROM Reservation r WHERE r.status = 'CONFIRMED'")
    @Query("SELECT COALESCE(SUM(r.totalPrice), 0) FROM Reservation r WHERE r.status = 'CONFIRMED' " +
            "AND r.voiture IS NOT NULL AND r.client IS NOT NULL")
    Double calculateTotalRevenue();

    //@Query("SELECT COUNT(r) FROM Reservation r WHERE r.status = 'Pending'")
    @Query("SELECT COUNT(r) FROM Reservation r WHERE r.status = 'Pending' " +
            "AND r.voiture IS NOT NULL AND r.client IS NOT NULL")
    long countPendingReservations();

    List<Reservation> findByClientId(Integer clientId);

    //@Query("SELECT r FROM Reservation r WHERE r.createdAt IS NOT NULL ORDER BY r.createdAt DESC")
    @Query("SELECT r FROM Reservation r WHERE r.createdAt IS NOT NULL AND r.voiture IS NOT NULL " +
            "AND r.client IS NOT NULL ORDER BY r.createdAt DESC")
    List<Reservation> findLatestReservations(Pageable pageable);

}
