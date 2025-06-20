package com.example.blood_donation.repository;

import com.example.blood_donation.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByBloodRequestIdAndAccountId(Long requestId, Long memberId);

    @Query("select a from Appointment a join fetch a.account join fetch a.bloodRequest")
    List<Appointment> findAllWithAssociations();
}
