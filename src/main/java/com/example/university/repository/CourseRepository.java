package com.example.university.repository;

import java.util.*;
import com.example.university.model.*;

public interface CourseRepository {
    List<Course> getCourses();

    Course getCoursesById(int courseId);

    Course addCourse(Course course);

    Course updateCoures(int courseId, Course course);

    void deleteCoures(int courseId);

    Professor getCoursesProfessor(int courseId);

    List<Student> getCoursesStudents(int courseId);

}
