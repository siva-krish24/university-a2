package com.example.university.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @ManyToMany(mappedBy = "students")
    @JsonIgnoreProperties("students")
    private List<Course> courses = new ArrayList<>();

    public Student() {

    }

    public Student(int id, String name, String email,List<Course> coures) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.courses = courses;
    }

    public int getStudentId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void set(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return name;
    }

    public void setStudentName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
         this.courses=courses;
    }
}
