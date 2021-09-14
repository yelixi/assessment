package com.sacc.assessment.repository;

import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Integer> {

    Page<Question> findAll(Pageable pageable);

    Question saveAndFlush(Question question);

    Optional<Question> findById(Integer integer);

    @Query(value = "select exam_paper_id from question where id = ?1", nativeQuery = true)
    Integer findExamPaperByQuestionId(Integer questionId);

}
