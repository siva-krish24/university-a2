package com.example.university.controller;

import com.example.university.model.Course;
import com.example.university.model.Professor;
import com.example.university.model.Student;
import com.example.university.service.CourseJpaService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@RestController
public class CourseController {

    @Autowired
    public CourseJpaService courseJpaService;

    @GetMapping("/courses")
    public List<Course> gatCourses() {
        return courseJpaService.getCourses();

    }

    @GetMapping("/courses/{courseId}")
    public Course getCourseById(@PathVariable("courseId") int courseId) {
        return courseJpaService.getCoursesById(courseId);
    }

    @PostMapping("/coureses")
    public Course addCourese(@RequestBody Course course) {
        return courseJpaService.addCourse(course);
    }

    @PutMapping("/courses/{courseId}")
    public Course updateCourse(@PathVariable("courseId") int courseId, @RequestBody Course course) {
        return courseJpaService.updateCoures(courseId, course);
    }

    @DeleteMapping("/courses/{courseId}")
    public void deleteCourese(@PathVariable("courseId") int courseId) {
        courseJpaService.deleteCoures(courseId);
    }

    @GetMapping("/courses/{courseId}/professor")
    public Professor getCourseProfessor(@PathVariable("courseId") int courseId) {
        return courseJpaService.getCoursesProfessor(courseId);
    }

    @GetMapping("/courese/{courseId}/students")
    public List<Student> getCourseStudents(@PathVariable("courseId") int courseId) {
        return courseJpaService.getCoursesStudents(courseId);
    }

}
