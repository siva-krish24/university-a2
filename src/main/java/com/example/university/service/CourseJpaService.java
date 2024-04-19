package com.example.university.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.university.model.Course;
import com.example.university.model.Professor;
import com.example.university.model.Student;
import com.example.university.repository.CourseJpaRepository;
import com.example.university.repository.CourseRepository;
import com.example.university.repository.ProfessorJpaRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service

public class CourseJpaService implements CourseRepository {
    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Autowired
    private ProfessorJpaRepository professorJPaRepository;
    @Autowired
    private StudentJpaService studentJPaRepository;
    @Override

    public List<Course> getCourses() {
        List<Course> courseList = courseJpaRepository.findAll().stream().collect(Collectors.toList());
        return new ArrayList<>(courseList);
    }

    @Override
    public Course getCoursesById(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            return course;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong courseId");
        }
    }

    @Override

    public Course addCourse(Course course) {
        Professor professor = course.getProfessor();
        int professorId = professor.getProfessorId();
        try {
            professor = professorJPaRepository.findById(professorId).get();
            courseJpaRepository.save(course);
            return course;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong professorId");

        }

    }

    @Override
    public Course updateCoures(int courseId, Course course) {
        try {
            Course newCourse = courseJpaRepository.findById(courseId).get();
            if (course.getCourseName() != null) {
                newCourse.setCourseName(course.getCourseName());

            }
            if (course.getCredits() != 0) {
                newCourse.setCredits(course.getCredits());
            }
            if (course.getProfessor() != null) {
                Professor professor = course.getProfessor();
                int professorId = professor.getProfessorId();
                Professor newprofessor = professorJPaRepository.findById(professorId).get();
                newCourse.setProfessor(newprofessor);
            }
            courseJpaRepository.save(newCourse);
            return newCourse;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Worng professorId");
        }    }

    @Override
    public void deleteCoures(int courseId) {
        try {
            courseJpaRepository.deleteById(courseId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Professor getCoursesProfessor(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();
            return professorJPaRepository.findById(course.getProfessor().getProfessorId()).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Student> getCoursesStudents(int courseId) {
        try {
            Course course = courseJpaRepository.findById(courseId).get();

            List<Student> students =  studentJPaRepository.getStudent();
            List<Integer> studentIds = new ArrayList<>();
            for(Student student : students){
                for(Course course1: student.getCourses()){
                    if (course1.getCourseId() == courseId){
                        studentIds.add(student.getStudentId());
                    }
                }
            }
            List<Student> resultList = new ArrayList<>();
            for(int id: studentIds){
                resultList.add(studentJPaRepository.getStudentById(id));
            }
            return resultList;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong professorId");
        }
    }






}
