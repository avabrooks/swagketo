package com.nighthawk.csa.database.role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
