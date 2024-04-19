package com.example.university.repository;

import java.util.List;

import com.example.university.model.Course;
import com.example.university.model.Student;

public interface StudentRepository {
    List<Student> getStudent();

    Student addStudentById(int studentId);

    Student getStudentById(int studentId);

    Student addStudent(Student student);

    Student updateStudent(int studentId, Student student);

    void deleteStudent(int studentId);

    List<Course> getStudentCourses(int studentId);

    List<Course> getStudentCourse(int studentId);
}
