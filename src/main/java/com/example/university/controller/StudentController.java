package com.example.university.controller;

import com.example.university.model.*;

import com.example.university.model.Student;
import com.example.university.service.StudentJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class StudentController {
    @Autowired
    public StudentJpaService studentJpaService;

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentJpaService.getStudent();
    }

    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable("studentId") int studentId) {
        return studentJpaService.getStudentById(studentId);
    }
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentJpaService.addStudent(student);
    }
    @PutMapping("/students{studentId}")
    public Student updateStudent(@PathVariable("studentId") int studentId, @RequestBody Student student) {
        return studentJpaService.updateStudent(studentId, student);
    }
    @DeleteMapping("student/{studentId}")
    public void deleteStudent(@PathVariable("studentId") int studentId) {
        studentJpaService.deleteStudent(studentId);
    }
    @GetMapping("/students/{studentId}/courses")
    public List<Course> getStudenCourses(@PathVariable("studentId") int studentId) {
        return studentJpaService.getStudentCourse(studentId);
    }
}
