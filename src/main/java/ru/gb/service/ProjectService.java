package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.entity.Project;
import ru.gb.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository repository;

    public Optional<Project> findById(Long id) {
        return repository.findById(id);
    }

    public List<Project> findAll() {
        return repository.findAll();
    }

    public Project save(Project project) {
        return repository.save(project);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
