package ru.gb.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "timesheets")
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "minutes")
    private Integer minutes;

    @Column(name = "creation_time")
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Override
    public String toString() {
        return "Timesheet{" +
                "id=" + id +
                ", minutes=" + minutes +
                ", createdAt=" + createdAt +
                ", project=" + project.getId() +
                '}';
    }
}
