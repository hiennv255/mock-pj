package com.vti.mockpj.database;


import com.vti.mockpj.models.roles.ERole;
import com.vti.mockpj.models.roles.Role;
import com.vti.mockpj.models.User;
import com.vti.mockpj.repository.RoleRepository;
import com.vti.mockpj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class Database {
//https://www.devglan.com/spring-boot/spring-boot-mongodb-configuration
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(RoleRepository roleRepository,
                                   UserRepository userRepository) {
        return new CommandLineRunner()
        {
            @Override
            public void run(String... args) throws Exception {
              //  new ConectorSQL().mostrarDatos();

                Role role1 = new Role();
                role1.setId(1);
                role1.setName(ERole.ROLE_ADMIN);

                Role role2 = new Role();
                role2.setId(2);
                role2.setName(ERole.ROLE_USER);

                if(roleRepository.findAll().size()==0){
                    roleRepository.save(role1);
                    roleRepository.save(role2);
                }

            // Generate user super admin
                if(userRepository.findAll().size()==0){
                    User admin = new User();
                    admin.setId(1l);
                    admin.setUserName("admin");
                    admin.setEmail("bg.hien@gmail.com");
                    admin.setFullName("Super");
                    admin.setCreatedTime(new Date());
                    admin.setParentID(0);
                    Set<Role> roles = new HashSet<>();
                    admin.setPassWord(passwordEncoder.encode("123abc@"));
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                    admin.setRoles(roles);
                    userRepository.save(admin);
                }
        }};
    }
}