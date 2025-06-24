package com.example.blood_donation.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"description"})
public class Role {
    @Id
    private String name;

    private String description;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "permission_role",
            joinColumns = @JoinColumn(name = "role_name"),
            inverseJoinColumns =  @JoinColumn(name = "permission_name")
    )
    private Set<Permission> permissions;
}
