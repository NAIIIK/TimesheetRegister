package ru.gb.page.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.entity.Project;
import ru.gb.page.dto.ProjectPageDto;
import ru.gb.service.ProjectService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectPageService {

    private final ProjectService projectService;

    public Optional<ProjectPageDto> findById(Long id) {
        return projectService.findById(id).map(this::convert);
    }

    public List<ProjectPageDto> findAll() {
        return projectService.findAll().stream()
                .map(this::convert)
                .toList();
    }

    private ProjectPageDto convert(Project project) {
        ProjectPageDto projectDto = new ProjectPageDto();

        projectDto.setId(String.valueOf(project.getProjectId()));
        projectDto.setName(project.getName());
        projectDto.setTimesheets(String.valueOf(project.getTimesheets()));

        return projectDto;
    }
}
