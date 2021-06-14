package com.sacc.assessment.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sacc.assessment.model.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 林夕
 * Date 2021/6/14 17:00
 */
@Slf4j
public class TigerLogoutSuccessHandler implements LogoutSuccessHandler {
    /**
     * 退出登陆url
     *      可以在yml或properties文件里通过nrsc.security.browser.signOutUrl 进行指定
     *      我指定的默认值为"/" --- 因为如果不指定一个默认的url时，配置授权那一块会报错
     */
    private final String signOutSuccessUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();
    public TigerLogoutSuccessHandler(String signOutSuccessUrl) {
        this.signOutSuccessUrl = signOutSuccessUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        log.info("退出成功");
        //如果没有指定退出成功的页面则返回前端一个json字符串
        if (StringUtils.equalsIgnoreCase("/",signOutSuccessUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(RestResult.success("退出成功")));
        } else {
            //重定向到退出成功登陆页面
            response.sendRedirect(signOutSuccessUrl);
        }
    }
}
