package com.project.diplomation.data.repositories;

import com.project.diplomation.data.models.entities.Review;
import com.project.diplomation.data.models.entities.UniversityTutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {
    List<Review> findByReviewerId(long reviewer_id);
    Review findByDateOfSubmission(LocalDate date);
    Review findReviewByThesisId(long id);

    @Query(value = """
        SELECT COUNT(DISTINCT a.student_id)
        FROM review r
        JOIN thesis t ON t.id = r.thesis_id
        JOIN application a ON a.id = t.application_id
        WHERE r.is_passed = false
        """, nativeQuery = true)
    Long countUniqueStudentsWithFailedReviews();
}
