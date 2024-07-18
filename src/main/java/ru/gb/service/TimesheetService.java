package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.entity.Project;
import ru.gb.entity.Timesheet;
import ru.gb.repository.ProjectRepository;
import ru.gb.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;

    private final ProjectRepository projectRepository;

    public Optional<Timesheet> findById (Long id) {
        return timesheetRepository.findById(id);
    }

    public List<Timesheet> findAll() {
        return timesheetRepository.findAll();
    }

    public List<Timesheet> findCreatedAfter(LocalDate dateAfter) {
        return timesheetRepository.findByCreatedAtAfter(dateAfter);
    }

    public List<Timesheet> findCreatedBefore(LocalDate dateBefore) {
        return timesheetRepository.findByCreatedAtBefore(dateBefore);
    }

    public Timesheet save(Timesheet timesheet) {
        Optional<Project> projectOpt = projectRepository.findById(timesheet.getProject().getId());
        if (projectOpt.isPresent()) {
            projectOpt.get().getTimesheets().add(timesheet);
            timesheet.setCreatedAt(LocalDate.now());
            return timesheetRepository.save(timesheet);
        }
        throw new IllegalArgumentException();
    }

    public void deleteById(Long id) {
        timesheetRepository.deleteById(id);
    }
}
