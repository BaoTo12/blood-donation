package com.example.blood_donation.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"description"})
public class Permission {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(unique = true, nullable = false)
    private String name;

    private String description;

}