package com.example.blood_donation.repository;

import com.example.blood_donation.entity.Donation;
import com.example.blood_donation.enumType.DonationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT d FROM Donation d WHERE d.appointment.id = :appointmentId")
    Optional<Donation> findByAppointmentId(@Param("appointmentId") Long appointmentId);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Donation d WHERE d.appointment.id = :appointmentId")
    boolean existsByAppointmentId(@Param("appointmentId") Long appointmentId);

    List<Donation> findByDonationType(DonationType donationType);

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d")
    Long getTotalDonationAmount();

    @Query("SELECT d.donationType, COUNT(d) FROM Donation d GROUP BY d.donationType")
    List<Object[]> getDonationCountByType();

}
