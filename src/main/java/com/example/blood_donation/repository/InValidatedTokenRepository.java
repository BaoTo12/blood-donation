package com.example.blood_donation.repository;

import com.example.blood_donation.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InValidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
}
