package com.sacc.assessment.form;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by 林夕
 * Date 2021/9/11 13:35
 */
@Data
@AllArgsConstructor
public class AnswerUserForm {

    private Integer answerId;

    private String studentId;

    private String username;
}
