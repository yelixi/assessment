package com.sacc.assessment.repository;

import com.sacc.assessment.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/8/1 20:10
 */
public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    List<Answer> findAllByQuestionIdAndExamPageId(Integer questionId,Integer examPageId);
//
    @Query(value = "select exam_paper_answer_id from answer where id = ?1", nativeQuery = true)
    Integer findPaperAnswerIdByAnswerId(Integer answerId);

}
