package com.sacc.assessment.repository;

import com.sacc.assessment.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Integer> {

    Page<Question> findAll(Pageable pageable);

    Question saveAndFlush(Question question);

    Optional<Question> findById(Integer integer);

}
