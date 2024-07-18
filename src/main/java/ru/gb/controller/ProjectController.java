package ru.gb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.entity.Project;
import ru.gb.entity.Timesheet;
import ru.gb.service.ProjectService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;

    @GetMapping("/{id}")
    public ResponseEntity<Project> get(@PathVariable Long id) {
        Optional<Project> projectOpt = service.findById(id);

        if (projectOpt.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(projectOpt.get());
        throw new NoSuchElementException();
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getProjectTimesheets (@PathVariable Long id) {
        Optional<Project> projectOpt = service.findById(id);
        if (projectOpt.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(projectOpt.get().getTimesheets());
        throw new NoSuchElementException();
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project project) {
        project = service.save(project);

        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);

        throw new NoSuchElementException();
    }
}
