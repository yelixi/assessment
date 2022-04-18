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
import java.util.HashMap;
import java.util.HashSet;
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
        System.out.println(examPaper.toString());
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
    public Page<ExamPaper> getMyAllExamPaper(Pageable pageable) {
        return examPaperRepository.findAll(pageable);
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
        List<Question> questionList = examPaper.getQuestions();
        List<Integer> questionIdList = new ArrayList<>();
        ExamPaperAnswer e = examPaperAnswers.get(0);
        e.getAnswers().forEach(answer -> questionIdList.add(answer.getQuestionId()));
        String[] questionNumber = {"后端通识第一题","后端通识第二题","后端通识第二题","后端通识第二题","后端通识第三题","前端通识第一题","前端通识第二题","前端通识第三题", "python组通识第一题",
                "python组通识第二题","python组通识第三题","安全通识第一题","安全通识第二题","安全通识第二题", "安全通识第三题",
                "后端专项第一题","后端专项第二题", "后端专项第三题","后端专项第四题","后端专项第四题","后端专项第四题","后端专项第四题","前端专项第一题","前端专项第二题","前端专项第三题","前端专项第四题","前端专项第五题","前端专项第五题","算法专项第一题","算法专项第二题",
                "算法专项第三题","算法专项第三题","算法专项第三题","算法专项第三题","python组专项第一题","python组专项第二题","python组专项第二题","python组专项第二题","安全组第一题","安全组第二题","安全组第三题"};
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表
        XSSFSheet sheet = workbook.createSheet("sheet");
        HashSet<String> set =new HashSet<>();
        set.add("back-2");
        set.add("Back-4");
        set.add("an-2");
        set.add("Front-5");
        set.add("Python-2");
        set.add("Su-3");

        for(int row = 0;row <examPaperAnswers.size()+1;row++){
            //List<ExamPaperAnswer> examPaperAnswerList = examPaperAnswerRepository.findAllByExamPaperId(examPaperId);
            XSSFRow sheetRow = sheet.createRow(row);
            if (row == 0){
                sheetRow.createCell(0).setCellValue("学号");
                sheetRow.createCell(1).setCellValue("姓名");
                for(int i=0;i< questionIdList.size();i++){
                    sheetRow.createCell(i+2).setCellValue(questionNumber[i]);
                }
                sheetRow.createCell(questionIdList.size()+2).setCellValue("通识题总分");
                sheetRow.createCell(questionIdList.size()+3).setCellValue("后端专项总分");
                sheetRow.createCell(questionIdList.size()+4).setCellValue("前端专项总分");
                sheetRow.createCell(questionIdList.size()+5).setCellValue("算法专项总分");
                sheetRow.createCell(questionIdList.size()+6).setCellValue("python专项总分");
                sheetRow.createCell(questionIdList.size()+7).setCellValue("安全专项总分");
                sheetRow.createCell(questionIdList.size()+8).setCellValue("exam_paper_answer_id");
            }else{
                ExamPaperAnswer examPaperAnswer = examPaperAnswers.get(row-1);
                User user = userRepository.getOne(examPaperAnswer.getUserId());
                sheetRow.createCell(0).setCellValue(user.getStudentId());
                sheetRow.createCell(1).setCellValue(user.getUsername());
                List<Answer> answers = examPaperAnswer.getAnswers();
                System.out.println(answers.size());
                System.out.println(questionIdList.size());
                System.out.println(examPaperAnswer.getId());
                //System.out.println(row-1);
                int totalScore = 0;
                int backScore = 0;
                int frontScore = 0;
                int suScore = 0;
                int pythonScore = 0;
                int anScore = 0;
                for(int i=0;i< questionIdList.size();i++){
                    Answer a = answers.get(i);
                    int score = 0;
                    if(a.getScore()!=null&&a.getScore().getGrade()!=null)
                        score = a.getScore().getGrade();
                    sheetRow.createCell(i+2).setCellValue(score/*+" "+a.getQuestionId()*/);
                    Question question = findQuestion(questionList,a.getQuestionId());
                    if(question.getGroupType().startsWith("B"))
                        backScore+=score;
                    else if(question.getGroupType().startsWith("F"))
                        frontScore+=score;
                    else if(question.getGroupType().startsWith("S"))
                        suScore+=score;
                    else if(question.getGroupType().startsWith("P"))
                        pythonScore+=score;
                    else if(question.getGroupType().startsWith("A"))
                        anScore+=score;
                    else totalScore+=score;
                }
                /*int k = 0;
                for(int i=0;i< questionList.size();i++){
                    Answer answer = answers.get(i);
                    int s = 0;
                    if(answer.getScore()!=null)
                        s=answer.getScore().getGrade();
                    if(set.contains(questionList.get(i).getGroupType())&&score==0){
                        sheetRow.createCell(k+2).setCellValue(score);
                    }
                }
                sheetRow.createCell(questionIdList.size()+2).setCellValue(totalScore);*/
                sheetRow.createCell(questionIdList.size()+2).setCellValue(totalScore);
                sheetRow.createCell(questionIdList.size()+3).setCellValue(backScore);
                sheetRow.createCell(questionIdList.size()+4).setCellValue(frontScore);
                sheetRow.createCell(questionIdList.size()+5).setCellValue(suScore);
                sheetRow.createCell(questionIdList.size()+6).setCellValue(pythonScore);
                sheetRow.createCell(questionIdList.size()+7).setCellValue(anScore);
                sheetRow.createCell(questionIdList.size()+8).setCellValue(examPaperAnswer.getId());
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

    Question findQuestion(List<Question> questionList,Integer id){
        for (Question question : questionList) {
            if (question.getId().equals(id))
                return question;
        }
        return null;
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

    @Override
    public List<ExamPaper> getMyUnfinishedExamPaper(UserDetail userDetail) {
        List<ExamPaper> unfinishedExams = examPaperRepository.findUnfinishedExamByUserId(userDetail.getId());
        List<ExamPaper> recentUnfinishedExams = new ArrayList<>();
        for(ExamPaper exam : unfinishedExams)
            if(exam.getEndTime().isAfter(LocalDateTime.now())) recentUnfinishedExams.add(exam);
        return recentUnfinishedExams;
    }
}
