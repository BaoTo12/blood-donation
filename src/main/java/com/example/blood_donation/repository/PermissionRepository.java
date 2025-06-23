package com.example.blood_donation.repository;

import com.example.blood_donation.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    void deleteByName(String permissionName);
    List<Permission> findAllByNameIn(List<String> names);
}