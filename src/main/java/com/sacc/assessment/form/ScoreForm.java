package com.sacc.assessment.form;

import lombok.Data;

/**
 * Created by 林夕
 * Date 2021/8/1 21:03
 */
@Data
public class ScoreForm {
    private Integer examPaperAnswerId;

    private Integer answerId;

    private Integer grade;

    private String comment;
}
