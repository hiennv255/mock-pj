package com.vti.mockpj.service;

import com.vti.mockpj.models.roles.ERole;
import com.vti.mockpj.models.roles.Role;
import com.vti.mockpj.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class InitialDataLoader {
	
	@Autowired
	private RoleRepository roleRepository;

	@Bean
	public ApplicationRunner initializer() {
		List<ERole> roles = Arrays.asList(ERole.ROLE_ADMIN, ERole.ROLE_USER, ERole.ROLE_DOCTOR);
	    return args -> roles.forEach(i -> createRoleIfNotFound(i));
	}
	
	private Optional<Role> createRoleIfNotFound(ERole roleName) {
        Optional<Role> role = roleRepository.findByName(roleName);
        if (!role.isPresent()) {
        	Role newRole = new Role();
        	newRole.setName(roleName);
        	newRole = roleRepository.save(newRole);
        }
        return role;
    }
}
