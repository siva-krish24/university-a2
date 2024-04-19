package com.example.university.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.university.model.*;

import com.example.university.repository.CourseJpaRepository;
import com.example.university.repository.ProfessorJpaRepository;

import com.example.university.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service

public class ProfessorJpaService implements ProfessorRepository {
    @Autowired
    private CourseJpaRepository courseJpaRepository;

    @Autowired
    private ProfessorJpaRepository professorJPaRepository;



    public Professor getProfessorById(int professorId) {
        try {
            Professor professor = professorJPaRepository.findById(professorId).get();
            return professor;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Professor> getProfessors() {
        return professorJPaRepository.findAll();
    }

    @Override
    public List<Course> getCoursesById(int professorId) {
        Professor professor = professorJPaRepository.findById(professorId).get();
        return courseJpaRepository.findAll().stream()
                .filter(course -> course.getProfessor().getProfessorId()==professorId).collect(Collectors.toList());
    }

    @Override

    public Professor addProfessor(Professor professor) {
        professorJPaRepository.save(professor);
        return professor;

    }

    @Override
    public Professor updateProfessor(int professorId, Professor professor) {
        try {
            Professor newProfessor = professorJPaRepository.findById(professorId).get();
            if (professor.getProfessorName() != null) {
                newProfessor.setProfessorName(professor.getProfessorName());
            }
            if (professor.getDepartment() != null) {
                newProfessor.setDepartment(professor.getDepartment());
            }
            professorJPaRepository.save(newProfessor);
            return newProfessor;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteProfessor(int professorId) {
        if(!professorJPaRepository.findById(professorId).isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try {
            Professor prof = professorJPaRepository.findById(professorId).get();
            List<Course> updatedCourses = new ArrayList<>();
            courseJpaRepository.findAll().forEach(course -> {
                if (course.getProfessor().getProfessorId() == professorId){
                    course.setProfessor(null);
                    updatedCourses.add(course);
                }
            });
//            courseJpaRepository.saveAll(updatedCourses);
            professorJPaRepository.delete(prof);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public Course getProfessorCourse(int professorId) {
        return null;
    }

}
