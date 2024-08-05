package ru.gb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Проекты", description = "API для работы с проектами")
public class ProjectController {

    private final ProjectService service;

    @Operation(summary = "Просмотр проекта", description = "Просмотр проекта по идентификатору", responses = {
            @ApiResponse(description = "success", responseCode = "200", content = @Content(schema = @Schema(implementation = Project.class))),
            @ApiResponse(description = "not found", responseCode = "404", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "internal error", content = @Content(schema = @Schema(implementation = Void.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Project> get(@PathVariable @Parameter(description = "ID проекта") Long id) {
        Optional<Project> projectOpt = service.findById(id);

        if (projectOpt.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(projectOpt.get());
        throw new NoSuchElementException();
    }

    @Operation(summary = "Просмотр всех проектов")
    @GetMapping
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @Operation(summary = "Просмотр табелей учета времени", description = "Просмотр табелей учета времени, относящихся к конкретному проекту")
    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getProjectTimesheets (@PathVariable Long id) {
        Optional<Project> projectOpt = service.findById(id);
        if (projectOpt.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(projectOpt.get().getTimesheets());
        throw new NoSuchElementException();
    }

    @Operation(summary = "Создание нового проекта")
    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project project) {
        project = service.save(project);

        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @Operation(summary = "Удаление проекта", description = "Удаление проекта по идентификатору")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "ID проекта") Long id) {
        service.deleteById(id);

        throw new NoSuchElementException();
    }
}
