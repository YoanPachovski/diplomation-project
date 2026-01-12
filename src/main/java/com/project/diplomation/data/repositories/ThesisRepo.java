package com.project.diplomation.data.repositories;

import com.project.diplomation.data.models.entities.Thesis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThesisRepo extends JpaRepository<Thesis, Long> {
    List<Thesis> findByTitleContaining(String title);
}