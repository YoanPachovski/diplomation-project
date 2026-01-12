package com.project.diplomation.data.repositories;

import com.project.diplomation.data.models.entities.Defense;
import com.project.diplomation.data.models.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DefenseRepo extends JpaRepository<Defense, Long> {
    List<Defense> findByDate(LocalDate date);
    List<Defense> findByGrade(int grade);
    Defense findByReview(Review review);
    Defense findById(long id);
    List<Defense> findDefenseByGradeBetween(int min, int max);
    Defense findDefenseByReview_Id(long id);
    List<Defense> findDefenseByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Defense> findDefenseByDateBetweenAndAndGradeBetween(LocalDate startDate, LocalDate endDate, int min, int max);

}
