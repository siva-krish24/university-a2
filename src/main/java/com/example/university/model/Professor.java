package com.example.university.model;

import javax.persistence.*;

@Entity
@Table(name = "professor")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int professorId;

    @Column(name = "name")
    private String name;

    @Column(name = "department")
    private String department;

    public Professor() {

    }

    public Professor(int professorId, String name, String department) {
        this.professorId = professorId;
        this.name = name;
        this.department = department;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public String getProfessorName() {
        return name;
    }

    public void setProfessorName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}