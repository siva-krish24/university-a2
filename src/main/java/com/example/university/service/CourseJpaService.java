package com.example.university.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.example.university.model.Course;
import com.example.university.model.Professor;
import com.example.university.model.Student;
import com.example.university.repository.CourseJpaRepository;
import com.example.university.repository.CourseRepository;
import com.example.university.repository.ProfessorJpaRepository;


import com.example.university.repository.StudentJpaRepository;
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
    private StudentJpaRepository studentJPaRepository;

    private HashMap<Integer, Object[]> professorsHashMap = new HashMap<>(); // Professor
    {
        professorsHashMap.put(1, new Object[] { "John Smith", "Computer Science" });
        professorsHashMap.put(2, new Object[] { "Mary Johnson", "Physics" });
        professorsHashMap.put(3, new Object[] { "David Lee", "Mathematics" });
        professorsHashMap.put(4, new Object[] { "Mark Willam", "Mathematics" }); // POST
        professorsHashMap.put(5, new Object[] { "Mark Williams", "Mathematics" }); // PUT
    }

    private HashMap<Integer, Object[]> coursesHashMap = new HashMap<>(); // Course
    {
        coursesHashMap.put(1, new Object[] { "Introduction to Programming", 3, 1, new Integer[] { 1, 2 } });
        coursesHashMap.put(2, new Object[] { "Quantum Mechanics", 4, 2, new Integer[] { 2, 3 } });
        coursesHashMap.put(3, new Object[] { "Calculus", 4, 3, new Integer[] { 1, 3, 4 } });
        coursesHashMap.put(4, new Object[] { "Statistics", 5, 3, new Integer[] { 2, 3 } }); // POST
        coursesHashMap.put(5, new Object[] { "Statistics", 4, 4, new Integer[] { 1, 3, 4 } }); // PUT
    }

    private HashMap<Integer, Object[]> studentsHashMap = new HashMap<>(); // Student
    {
        studentsHashMap.put(1,
                new Object[] { "Alice Johnson", "alice@example.com", new Integer[] { 1, 3, 4 } });
        studentsHashMap.put(2, new Object[] { "Bob Davis", "bob@example.com", new Integer[] { 1, 2 } });
        studentsHashMap.put(3, new Object[] { "Eva Wilson", "eva@example.com", new Integer[] { 2, 3, 4 } });
        studentsHashMap.put(4, new Object[] { "Harley Hoies", "harley@example.com", new Integer[] { 2, 4 } }); // POST
        studentsHashMap.put(5, new Object[] { "Harley Homes", "harley@example.com", new Integer[] { 3, 4 } }); // PUT
    }
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

       course.getStudents().forEach(student -> {
           boolean isStudentPresent = studentJPaRepository.findById(student.getStudentId()).isPresent();
           if(isStudentPresent){
               studentJPaRepository.findById(student.getStudentId()).get().getCourses().add(course);
           }
           else{
               student.getCourses().add(course);
               studentJPaRepository.save(student);
           }
        });
        try {
            courseJpaRepository.save(course);
            return course;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong professorId");

        }

    }

    @Override
    public Course updateCoures(int courseId, Course course) {
        try {
            if (course.getCourseName() != null) {
                courseJpaRepository.findById(courseId).get().setCourseName(course.getCourseName());

            }
            if (course.getCredits() != 0) {
                courseJpaRepository.findById(courseId).get().setCredits(course.getCredits());
            }
            if (course.getProfessor() != null) {
                Professor newProfessor = professorJPaRepository.findById( course.getProfessor().getProfessorId()).get();
                courseJpaRepository.findById(courseId).get().setProfessor(newProfessor);
            }
            if (course.getStudents().size() > 0) {
                courseJpaRepository.findById(courseId).get().getStudents().clear();
                course.getStudents().forEach(student -> {
                    boolean isStudentPresent = studentJPaRepository.findById(student.getStudentId()).isPresent();
                    if(isStudentPresent){
                        Student existingStudent =  studentJPaRepository.findById(student.getStudentId()).get();
                        courseJpaRepository.findById(courseId).get().getStudents().add(existingStudent);
                    }
                    else{
                        student.getCourses().add(course);
                        studentJPaRepository.save(student);
                        courseJpaRepository.findById(courseId).get().getStudents().add(student);
                    }
                });
            }
            Course newCourse =  courseJpaRepository.findById(courseId).get();
//            courseJpaRepository.deleteById(courseId);
            courseJpaRepository.save(newCourse);
            return  courseJpaRepository.findById(courseId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Worng professorId");
        }    }

    @Override
    public void deleteCoures(int courseId) {
        try {
            studentJPaRepository.findAll().forEach(student -> {
                List<Course> updatedCourses = new ArrayList<>();
                student.getCourses().forEach(course -> {
                    if(course.getCourseId() != courseId){
                        updatedCourses.add(course);
                    }
                });
                student.setCourses(updatedCourses);
            });
            courseJpaRepository.deleteById(courseId);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);

        }catch (ResponseStatusException re) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
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

            List<Student> students =  studentJPaRepository.findAll();
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
                resultList.add(studentJPaRepository.findById(id).get());
            }
            return resultList;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong professorId");
        }
    }






}
