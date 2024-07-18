package ru.gb.page.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.page.dto.TimesheetPageDto;
import ru.gb.page.service.TimesheetPageService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/home/timesheets")
@RequiredArgsConstructor
public class TimesheetPageController {

    private final TimesheetPageService service;

    @GetMapping
    public String getTimesheetsPage(Model model) {
        List<TimesheetPageDto> timesheets = service.findAll();
        model.addAttribute("timesheets", timesheets);
        return "timesheets-page.html";
    }

    @GetMapping("/{id}")
    public String getTimesheetPage(@PathVariable Long id, Model model) {
        Optional<TimesheetPageDto> timesheetOpt = service.findById(id);

        if (timesheetOpt.isPresent()) {
            model.addAttribute("timesheet", timesheetOpt.get());

            return "timesheet-page.html";
        }

        throw new NoSuchElementException();
    }
}
