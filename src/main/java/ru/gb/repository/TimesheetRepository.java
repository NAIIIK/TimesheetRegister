package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.entity.Timesheet;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    List<Timesheet> findByCreatedAtAfter(LocalDate dateAfter);

    List<Timesheet> findByCreatedAtBefore(LocalDate dateBefore);
}
