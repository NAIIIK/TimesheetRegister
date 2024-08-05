package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.entity.Employee;
import ru.gb.entity.Project;
import ru.gb.entity.Timesheet;
import ru.gb.repository.EmployeeRepository;
import ru.gb.repository.ProjectRepository;
import ru.gb.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;

    private final ProjectRepository projectRepository;

    private final EmployeeRepository employeeRepository;

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
        Optional<Project> projectOpt = projectRepository.findById(timesheet.getProject().getProjectId());
        Optional<Employee> employeeOpt = employeeRepository.findById(timesheet.getEmployee().getEmployeeId());

        if (projectOpt.isPresent() && employeeOpt.isPresent()) {
            projectOpt.get().getTimesheets().add(timesheet);
            employeeOpt.get().getTimesheets().add(timesheet);
            timesheet.setCreatedAt(LocalDate.now());
            return timesheetRepository.save(timesheet);
        }

        throw new NoSuchElementException();
    }

    public void deleteById(Long id) {
        timesheetRepository.deleteById(id);
    }
}
