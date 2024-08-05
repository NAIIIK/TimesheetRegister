package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.entity.Employee;
import ru.gb.entity.EmployeeProject;
import ru.gb.entity.Project;
import ru.gb.repository.EmployeeProjectRepository;
import ru.gb.repository.EmployeeRepository;
import ru.gb.repository.ProjectRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeProjectService {

    private final EmployeeProjectRepository employeeProjectRepository;

    private final EmployeeRepository employeeRepository;

    private final ProjectRepository projectRepository;

    public EmployeeProject save(Long employeeId, Long projectId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        Optional<Project> projectOpt = projectRepository.findById(projectId);

        if (employeeOpt.isPresent() && projectOpt.isPresent()) {
            EmployeeProject employeeProject = new EmployeeProject();

            employeeProject.setEmployee(employeeOpt.get());
            employeeProject.setProject(projectOpt.get());

            return employeeProjectRepository.save(employeeProject);
        }

        throw new NoSuchElementException();
    }
}
