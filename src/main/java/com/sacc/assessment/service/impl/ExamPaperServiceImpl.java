package com.sacc.assessment.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.sacc.assessment.entity.*;
import com.sacc.assessment.enums.ResultEnum;
import com.sacc.assessment.exception.BusinessException;
import com.sacc.assessment.repository.AnswerRepository;
import com.sacc.assessment.repository.ExamPaperAnswerRepository;
import com.sacc.assessment.repository.ExamPaperRepository;
import com.sacc.assessment.model.UserDetail;
import com.sacc.assessment.repository.UserRepository;
import com.sacc.assessment.service.ExamPaperService;
import com.sacc.assessment.util.GetNullPropertyNamesUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 林夕
 * Date 2021/8/1 12:53
 */
@Service
@Slf4j
public class ExamPaperServiceImpl implements ExamPaperService {

    @Resource
    private ExamPaperRepository examPaperRepository;

    @Resource
    private AnswerRepository answerRepository;

    @Resource
    private ExamPaperAnswerRepository examPaperAnswerRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    public ExamPaper createExam(ExamPaper examPaper, UserDetail userDetail) {
        examPaper.setUserId(userDetail.getId());
        examPaper.setCreatedAt(LocalDateTime.now());
        examPaper.setUpdatedAt(LocalDateTime.now());
        examPaper.getQuestions().forEach(x->x.setCreatedAt(LocalDateTime.now()));
        examPaper.getQuestions().forEach(x->x.setUpdatedAt(LocalDateTime.now()));
        Integer totalScore = examPaper.getQuestions().stream().mapToInt(Question::getScore).sum();
        examPaper.setExamTime(Math.toIntExact(LocalDateTimeUtil.between(examPaper.getStartTime(), examPaper.getEndTime()).toMinutes()));
        examPaper.setExamTotalScore(totalScore);
        examPaperRepository.save(examPaper);
        return examPaper;
    }

    @Override
    public boolean updateExam(ExamPaper examPaper) {
        ExamPaper exam = examPaperRepository.getOne(examPaper.getId());
        exam.setStartTime(examPaper.getStartTime());
        exam.setEndTime(examPaper.getEndTime());
        exam.setName(examPaper.getName());
        if(examPaper.getCreatedAt() != null)  exam.setCreatedAt(examPaper.getCreatedAt());
        exam.setUpdatedAt(LocalDateTime.now());
        exam.setUserId(examPaper.getUserId());
        for(Question question: exam.getQuestions()){
            for(Question q: examPaper.getQuestions()){
                if(question.getId().equals(q.getId())) {
                    BeanUtils.copyProperties(q, question, GetNullPropertyNamesUtil.getNullPropertyNames(q));
                    q.setCreatedAt(LocalDateTime.now());
                }
            }
        }

        for(Question question: examPaper.getQuestions()){
            if(question.getId()==null){
                question.setCreatedAt(LocalDateTime.now());
                question.setUpdatedAt(LocalDateTime.now());
                question.setCreatUser(examPaper.getUserId());
                exam.getQuestions().add(question);
            }
        }
        Integer totalScore = exam.getQuestions().stream().mapToInt(Question::getScore).sum();
        exam.setExamTotalScore(totalScore);
        exam.setExamTime(Math.toIntExact(LocalDateTimeUtil.between(exam.getStartTime(), exam.getEndTime()).toMinutes()));
        examPaperRepository.save(exam);
        return true;
    }

    @Override
    public Page<ExamPaper> getMyAllExamPaper(UserDetail userDetail, Pageable pageable) {
        return examPaperRepository.findAllByUserId(userDetail.getId(),pageable);
    }

    @Override
    public ExamPaper getExamPaper(Integer examPaperId, UserDetail userDetail) {
        return examPaperRepository.findByIdAndUserId(examPaperId, userDetail.getId());
    }

    @Override
    public ExamPaper getExam(Integer examId) {
        return examPaperRepository.getOne(examId);
    }

    @Override
    public List<ExamPaper> getAllExam() {
        return examPaperRepository.findAll();
    }

    @Override
    public List<Answer> answerList(Integer questionId, Integer examPageId) {
        return answerRepository.findAllByQuestionIdAndExamPageId(questionId,examPageId);
    }

    @Override
    public boolean getAllScore(Integer examPaperId, HttpServletResponse resp) {
        List<ExamPaperAnswer> examPaperAnswers = examPaperAnswerRepository.findAllByExamPaperId(examPaperId);
        ExamPaper examPaper = examPaperRepository.getOne(examPaperId);
        if(examPaperAnswers==null)
            return false;
        //获取问题列表
        List<Integer> questionIdList = new ArrayList<>();
        ExamPaperAnswer e = examPaperAnswers.get(0);
        e.getAnswers().forEach(answer -> questionIdList.add(answer.getQuestionId()));
        String[] questionNumber = {"第一题","第二题","第三题","第四题","第五题","第六题","第七题", "第八题","第九题",
                "第十题","第十一题","第十二题","第十三题","第十四题","第十五题","第十六题","第十七题","第十八题","第十九题","第二十题"};
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表
        XSSFSheet sheet = workbook.createSheet("sheet");

        for(int row = 0;row <examPaperAnswers.size()+1;row++){
            XSSFRow sheetRow = sheet.createRow(row);
            if (row == 0){
                sheetRow.createCell(0).setCellValue("学号");
                sheetRow.createCell(1).setCellValue("姓名");
                for(int i=0;i< questionIdList.size();i++){
                    sheetRow.createCell(i+2).setCellValue(questionNumber[i]);
                }
                sheetRow.createCell(questionIdList.size()+2).setCellValue("总分");
            }else{
                ExamPaperAnswer examPaperAnswer = examPaperAnswers.get(row-1);
                User user = userRepository.getOne(examPaperAnswer.getUserId());
                sheetRow.createCell(0).setCellValue(user.getStudentId());
                sheetRow.createCell(1).setCellValue(user.getUsername());
                List<Answer> answers = examPaperAnswer.getAnswers();
                int totalScore = 0;
                for(int i=0;i< questionIdList.size();i++){
                    Answer a = answers.get(i);
                    sheetRow.createCell(i+2).setCellValue(a.getScore().getGrade());
                    totalScore+=a.getScore().getGrade();
                }
                sheetRow.createCell(questionIdList.size()+2).setCellValue(totalScore);
            }
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            workbook.write(os);
            byte[] bytes = os.toByteArray();
            resp.reset();
            //Content-disposition 是 MIME 协议的扩展，MIME 协议指示 MIME 用户代理如何显示附加的文件。
            // Content-disposition其实可以控制用户请求所得的内容存为一个文件的时候提供一个默认的文件名，
            // 文件直接在浏览器上显示或者在访问时弹出文件下载对话框。
            resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(examPaper.getName()+".xlsx", StandardCharsets.UTF_8));
            resp.setContentType("application/x-msdownload");
            resp.setCharacterEncoding("utf-8");
            resp.getOutputStream().write(bytes);
            resp.getOutputStream().flush();
            resp.getOutputStream().close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean isInExamTime(Integer examId) {
        ExamPaper examPaper = examPaperRepository.getOne(examId);
        if(examPaper.getStartTime().isAfter(LocalDateTime.now()))
            throw new BusinessException(ResultEnum.EXAM_IS_NOT_START);
        if(examPaper.getEndTime().isBefore(LocalDateTime.now()))
            throw new BusinessException(ResultEnum.EXAM_IS_END);
        return true;
    }
}
