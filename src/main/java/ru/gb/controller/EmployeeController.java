package ru.gb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Сотрудники", description = "API для работы с сотрудниками")
public class EmployeeController {

    private final EmployeeService service;

    @Operation(summary = "Просмотр сотрудника", description = "Просмотр сотрудника по идентификатору")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable @Parameter(description = "ID сотрудника") Long id) {
        Optional<Employee> employeeOpt = service.findById(id);

        if (employeeOpt.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(employeeOpt.get());

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Просмотр табелей учета времени", description = "Просмотр табелей учета времени, относящихся к конкретному сотруднику")
    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getEmployeeTimesheets(@PathVariable @Parameter(description = "ID сотрудника") Long id) {
        Optional<Employee> employeeOpt = service.findById(id);

        if (employeeOpt.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(employeeOpt.get().getTimesheets());

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Просмотр проектов", description = "Просмотр проектов, относящихся к конкретному сотруднику")
    @GetMapping("/{id}/projects")
    public ResponseEntity<List<Project>> getEmployeeProjects(@PathVariable @Parameter(description = "ID сотрудника") Long id) {
        Optional<Employee> employeeOpt = service.findById(id);

        if (employeeOpt.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(employeeOpt.get().getProjects());

        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Просмотр всех сотрудников")
    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @Operation(summary = "Создание нового сотрудника")
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        employee = service.save(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @Operation(summary = "Удаление сотрудника", description = "Удаление сотрудника по идентификатору")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @Parameter(description = "ID сотрудника") Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
