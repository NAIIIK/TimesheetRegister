package ru.gb.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class EmployeeProjectId implements Serializable {
    private Long employeeId;
    private Long projectId;
}
