package com.example.university.service;

import java.util.ArrayList;
import java.util.List;

import com.example.university.model.*;
import com.example.university.repository.CourseJpaRepository;
import com.example.university.repository.StudentJpaRepository;
import com.example.university.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service

public class StudentJpaService implements StudentRepository {
    @Autowired
    private StudentJpaRepository studentJpaRepository;
    @Autowired
    private CourseJpaRepository courseJpaRepository;
    @Override

    public List<Student> getStudent() {
        return studentJpaRepository.findAll();
    }

    @Override
    public Student addStudentById(int studentId) {
        return null;
    }

    @Override
    public Student getStudentById(int studentId) {
        try {
            Student student = studentJpaRepository.findById(studentId).get();
            return student;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student addStudent(Student student) {
        if(studentJpaRepository.findById(student.getStudentId()).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        student.getCourses().forEach(course -> {
            if(!courseJpaRepository.findById(course.getCourseId()).isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        });
//        List<Integer> courseIds = new ArrayList<>();
//        student.getCourses().forEach(course -> {
//           courseIds.add(course.getCourseId());
//        });
//        List<Course> courses = new ArrayList<>();
//        courseIds.forEach(courseId ->{
//            courses.add(courseJpaRepository.findById(courseId).get());
//        });
//        student.setCourses(courses);
        studentJpaRepository.save(student);
        return studentJpaRepository.findById(student.getStudentId()).get();
    
    }

    @Override 
    public Student updateStudent(int studentId, Student student) {
        if (!studentJpaRepository.findById(studentId).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try {
            Student newStudent = studentJpaRepository.findById(studentId).get();
            if(student.getCourses().size()>0){
                List<Course> courses = student.getCourses();
                courses.forEach(course -> {
                    if(!courseJpaRepository.findById(course.getCourseId()).isPresent()){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                    }
                });
                newStudent.setCourses(courses);
            }
            if (student.getStudentName() != null)
                newStudent.setStudentName(student.getStudentName());
            if (student.getEmail() != null)
                newStudent.setEmail(student.getEmail());
           studentJpaRepository.save(newStudent);
           return newStudent;
         } catch (ResponseStatusException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
         } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }
    }

    @Override
    public void deleteStudent(int studentId) {
        if(!studentJpaRepository.findById(studentId).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try {
            List<Course> updatedCourses = new ArrayList<>();
            courseJpaRepository.findAll().forEach(course -> {
                List<Student> newStudentList = new ArrayList<>();
                course.getStudents().forEach(student -> {
                    if (student.getStudentId() != studentId){
                        newStudentList.add(student);
                    }
                });
                course.setStudents(newStudentList);

            });
            studentJpaRepository.deleteById(studentId);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);

        }catch (ResponseStatusException re){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Course> getStudentCourses(int studentId) {
        return null;
    }

    @Override
    public List<Course> getStudentCourse(int studentId) {
       return studentJpaRepository.findById(studentId).get().getCourses();
    }

}
