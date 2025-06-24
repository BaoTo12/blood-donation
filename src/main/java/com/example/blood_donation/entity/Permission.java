package com.example.blood_donation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"description"})
public class Permission {
    @Id
    private String name;

    private String description;

}