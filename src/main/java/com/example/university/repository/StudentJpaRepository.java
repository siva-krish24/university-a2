package com.example.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.university.model.*;

@Repository
public interface StudentJpaRepository extends JpaRepository<Student, Integer> {

}
