package com.sacc.assessment.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sacc.assessment.model.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 林夕
 * Date 2021/6/14 16:42
 */
public class ResponseUtil {
    public static<T> void restResponse(HttpServletResponse resp, HttpStatus status, RestResult<T> responseVo) throws IOException {
        resp.setStatus(status.value());
        resp.setContentType(MediaType.APPLICATION_JSON.toString());
        resp.getOutputStream().write(new ObjectMapper().writeValueAsBytes(responseVo));
    }
}
