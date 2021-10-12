package com.example.shop.config;

import com.example.shop.domain.dao.Role;
import com.example.shop.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
public class AppConfig {
//    @Bean
//    public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
//        return args -> {
//            createRoleIfNeeded(roleRepository, "ROLE_USER");
//            createRoleIfNeeded(roleRepository, "ROLE_ADMIN");
//        };
//    }

//    public void createRoleIfNeeded(RoleRepository roleRepository, String roleName) {
//        Optional<Role> role = roleRepository.findByName(roleName);
//        if (role.isEmpty()) {
//            roleRepository.save(new Role(null, roleName));
//        }
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
