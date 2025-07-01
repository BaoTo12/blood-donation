package com.example.blood_donation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvalidatedToken {

    @Id
    private String id;
    private Date expiryTime;
}
