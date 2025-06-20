package com.example.blood_donation.repository;

import com.example.blood_donation.entity.Health;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthRepository extends JpaRepository<Health, Long> {

    @Query("SELECT h FROM Health h WHERE h.appointment.id = :appointmentId")
    Optional<Health> findByAppointmentId(@Param("appointmentId") Long appointmentId);

    @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM Health h WHERE h.appointment.id = :appointmentId")
    boolean existsByAppointmentId(@Param("appointmentId") Long appointmentId);
}