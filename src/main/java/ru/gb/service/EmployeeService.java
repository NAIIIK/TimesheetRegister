package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.entity.Employee;
import ru.gb.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public Optional<Employee> findById(Long id) {
        return repository.findById(id);
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee save (Employee employee) {
        return repository.save(employee);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
