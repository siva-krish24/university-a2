package com.example.university.repository;

import com.example.university.model.Course;
import com.example.university.model.Professor;

import java.util.List;

public interface ProfessorRepository {
    List<Professor> getProfessors();

   List<Course> getCoursesById(int ProfessorId);

   Professor addProfessor(Professor professor);

   Professor updateProfessor(int professorId,Professor professor);

    void deleteProfessor(int professorId);

    Course getProfessorCourse(int professorId);

}
