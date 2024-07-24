package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
