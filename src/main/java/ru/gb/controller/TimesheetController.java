package ru.gb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.entity.Timesheet;
import ru.gb.service.TimesheetService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/timesheets")
@RequiredArgsConstructor
@Tag(name = "Табели учета рабочего времени", description = "API для работы с табелями учета рабочего времени")
public class TimesheetController {

    private final TimesheetService service;

    @Operation(summary = "Просмотр табеля", description = "Просмотр табеля по идентификатору")
    @GetMapping("/{id}")
    public ResponseEntity<Timesheet> get(@PathVariable @Parameter(description = "ID табеля") Long id) {
        Optional<Timesheet> timesheetOpt = service.findById(id);

        if (timesheetOpt.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(timesheetOpt.get());
        throw new NoSuchElementException();
    }

    @Operation(summary = "Просмотр всех табелей", description = "Просмотр всех табелей с возможностью фильтрации")
    @GetMapping
    public ResponseEntity<List<Timesheet>> getAll(@RequestParam(value = "createdAfter", required = false) @Parameter(description = "Дата для фильтрации табелей, созданных позже указанного значения") String createdAfter,
                                                  @RequestParam(value = "createdBefore", required = false) @Parameter(description = "Дата для фильтрации табелей, созданных раньше указанного значения") String createdBefore) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        if (createdAfter != null) {
            LocalDate dateAfter = LocalDate.parse(createdAfter, formatter);

            List<Timesheet> timesheetsAfter = service.findCreatedAfter(dateAfter);

            if (timesheetsAfter.isEmpty()) return ResponseEntity.noContent().build();
            return ResponseEntity.status(HttpStatus.OK).body(timesheetsAfter);
        }

        if (createdBefore != null) {
            LocalDate dateBefore = LocalDate.parse(createdBefore, formatter);

            List<Timesheet> timesheetsBefore = service.findCreatedBefore(dateBefore);

            if (timesheetsBefore.isEmpty()) return ResponseEntity.noContent().build();

            return ResponseEntity.status(HttpStatus.OK).body(timesheetsBefore);
        }

        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @Operation(summary = "Создание нового табеля")
    @PostMapping
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
        timesheet = service.save(timesheet);

        if (timesheet == null) throw  new IllegalArgumentException();

        return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
    }

    @Operation(summary = "Удаление табеля", description = "Удаление табеля по идентификатору")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "ID табеля") Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
