package com.example.blood_donation.service.impl;

import com.example.blood_donation.dto.requests.permission.PermissionRequest;
import com.example.blood_donation.dto.responses.permission.PermissionResponse;
import com.example.blood_donation.entity.Permission;
import com.example.blood_donation.exception.AppException;
import com.example.blood_donation.exception.ErrorCode;
import com.example.blood_donation.mapper.PermissionMapper;
import com.example.blood_donation.repository.PermissionRepository;
import com.example.blood_donation.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request) {
        if (permissionRepository.findById(request.getName()).isPresent()) {
            throw new AppException(ErrorCode.DUPLICATE_RESOURCE,
                    "There is another Permission with name: " + request.getName());
        }
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Transactional(readOnly = true)
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
