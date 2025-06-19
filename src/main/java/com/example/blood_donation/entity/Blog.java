package com.example.blood_donation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Setter
@Getter
@ToString
@DynamicUpdate
public class Blog extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    @ToString.Exclude
    private Account account;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

}
