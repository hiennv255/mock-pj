package com.vti.mockpj.repository;


import com.vti.mockpj.models.roles.ERole;
import com.vti.mockpj.models.roles.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}
