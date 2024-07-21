package ru.gb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.entity.Employee;
import ru.gb.entity.Project;
import ru.gb.entity.Timesheet;
import ru.gb.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        Optional<Employee> employeeOpt = service.findById(id);

        if (employeeOpt.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(employeeOpt.get());

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getEmployeeTimesheets(@PathVariable Long id) {
        Optional<Employee> employeeOpt = service.findById(id);

        if (employeeOpt.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(employeeOpt.get().getTimesheets());

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/projects")
    public ResponseEntity<List<Project>> getEmployeeProjects(@PathVariable Long id) {
        Optional<Employee> employeeOpt = service.findById(id);

        if (employeeOpt.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(employeeOpt.get().getProjects());

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        employee = service.save(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
