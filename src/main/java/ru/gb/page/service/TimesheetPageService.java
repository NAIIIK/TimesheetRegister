package ru.gb.page.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.entity.Project;
import ru.gb.entity.Timesheet;
import ru.gb.page.dto.TimesheetPageDto;
import ru.gb.service.ProjectService;
import ru.gb.service.TimesheetService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimesheetPageService {

    private final TimesheetService timesheetService;
    private final ProjectService projectService;

    public Optional<TimesheetPageDto> findById(Long id) {
        return timesheetService.findById(id).map(this::convert);
    }

    public List<TimesheetPageDto> findAll() {
        return timesheetService.findAll().stream()
                .map(this::convert)
                .toList();
    }

    private TimesheetPageDto convert(Timesheet timesheet) {
        Optional<Project> projectOpt = projectService.findById(timesheet.getProject().getProjectId());

        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();

            TimesheetPageDto timesheetDto = new TimesheetPageDto();

            timesheetDto.setId(String.valueOf(timesheet.getId()));
            timesheetDto.setProjectName(project.getName());
            timesheetDto.setProjectId(String.valueOf(project.getProjectId()));
            timesheetDto.setMinutes(String.valueOf(timesheet.getMinutes()));
            timesheetDto.setCreatedAt(String.valueOf(timesheet.getCreatedAt()));

            return timesheetDto;
        }
        throw new IllegalArgumentException();
    }
}
