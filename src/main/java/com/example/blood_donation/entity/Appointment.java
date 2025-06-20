package com.example.blood_donation.entity;

import com.example.blood_donation.enumType.AppointmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                name = "uk_appointments_request_member",
                columnNames = {"request_id", "member_id"}
        )
)
@Setter
@Getter
public class Appointment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "account_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_appointment_member")
    )
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "request_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_appointment_request")
    )
    private BloodRequest bloodRequest;


    private String status;
}
