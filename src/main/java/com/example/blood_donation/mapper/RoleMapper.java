package com.example.blood_donation.mapper;

import com.example.blood_donation.dto.request.role.RoleRequest;
import com.example.blood_donation.dto.response.role.RoleResponse;
import com.example.blood_donation.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
