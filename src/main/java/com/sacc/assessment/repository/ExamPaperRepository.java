package com.sacc.assessment.repository;

import com.sacc.assessment.entity.ExamPaper;
import com.sacc.assessment.entity.ExamPaperAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by 林夕
 * Date 2021/8/1 12:39
 */
public interface ExamPaperRepository extends JpaRepository<ExamPaper,Integer> {

    Page<ExamPaper> findAllByUserId(Integer userId, Pageable pageable);

    ExamPaper findByIdAndUserId(Integer id,Integer userId);

    @Query(
            value = "select * from exam_paper" +
                    " where (select count(1) from exam_paper_answer where user_id =?1 and exam_paper_answer.exam_paper_id = exam_paper.id) = 0 " +
                    " order by exam_paper.start_time  ASC" ,

            nativeQuery = true)
    List<ExamPaper> findUnfinishedExamByUserId(Integer userId);
}
