package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
