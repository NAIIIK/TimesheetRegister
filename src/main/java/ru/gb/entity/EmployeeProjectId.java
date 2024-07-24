package ru.gb.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class EmployeeProjectId {

    private Long employeeId;
    private Long projectId;

}
