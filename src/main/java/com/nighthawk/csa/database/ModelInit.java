package com.nighthawk.csa.database;

import com.nighthawk.csa.database.role.Role;
import com.nighthawk.csa.database.role.RoleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component // Scans Application for ModelInit Bean, this detects CommandLineRunner
public class ModelInit {
    // Inject repositories
    @Autowired RoleJpaRepository roleJpaRepository;

    @Bean
    CommandLineRunner run() {  // The run() method will be executed after the application starts
        return args -> {
            // Fail safe data validations

            // make sure Role database is populated with defaults
            String[] roles = {"ROLE_STUDENT", "ROLE_TEACHER", "ROLE_ADMIN", "ROLE_TESTER"};
            for (String role : roles) {
                if (roleJpaRepository.findByName(role) == null)
                    roleJpaRepository.save(new Role(role));
            }


        };
    }
}