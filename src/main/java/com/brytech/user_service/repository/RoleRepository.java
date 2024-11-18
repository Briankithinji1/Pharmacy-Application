package com.brytech.user_service.repository;

import com.brytech.user_service.enums.RoleType;
import com.brytech.user_service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleType name);
}
