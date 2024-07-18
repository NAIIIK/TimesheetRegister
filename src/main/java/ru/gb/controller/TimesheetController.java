package ru.gb.controller;

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
public class TimesheetController {

    private final TimesheetService service;

    @GetMapping("/{id}")
    public ResponseEntity<Timesheet> get(@PathVariable Long id) {
        Optional<Timesheet> timesheetOpt = service.findById(id);

        if (timesheetOpt.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(timesheetOpt.get());
        throw new NoSuchElementException();
    }

    @GetMapping
    public ResponseEntity<List<Timesheet>> getAll(@RequestParam(value = "createdAfter", required = false) String createdAfter,
                                                  @RequestParam(value = "createdBefore", required = false) String createdBefore) {
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

    @PostMapping
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
        timesheet = service.save(timesheet);

        if (timesheet == null) throw  new IllegalArgumentException();

        return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
