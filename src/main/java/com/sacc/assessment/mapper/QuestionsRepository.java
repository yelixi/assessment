package com.sacc.assessment.mapper;

import com.sacc.assessment.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsRepository extends JpaRepository<Question, Integer> {
}
