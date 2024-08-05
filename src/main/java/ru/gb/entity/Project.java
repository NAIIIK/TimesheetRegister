package ru.gb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long projectId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "project",
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonIgnore
    private List<Timesheet> timesheets = new ArrayList<>();

    @ManyToMany(mappedBy = "projects")
    private List<Employee> employees = new ArrayList<>();

    @Override
    public String toString() {
        return "Project{" +
                "id=" + projectId +
                ", name='" + name + '\'' +
                ", timesheets=" + timesheets +
                '}';
    }
}
