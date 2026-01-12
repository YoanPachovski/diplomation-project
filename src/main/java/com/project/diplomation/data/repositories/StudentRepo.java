package com.project.diplomation.data.repositories;

import com.project.diplomation.data.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepo extends JpaRepository<Student, Long> {
    List<Student> findByName(String name);
    Student findByfNumber(String fNumber);
}