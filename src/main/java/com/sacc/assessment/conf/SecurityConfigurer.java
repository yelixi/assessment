package com.sacc.assessment.conf;

import com.sacc.assessment.handler.TigerLogoutSuccessHandler;
import com.sacc.assessment.model.RestResult;

import com.sacc.assessment.util.ResponseUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Created by 林夕
 * Date 2021/6/14 15:48
 */
@Configuration
/*开启方法权限认证*/
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final TigerLogoutSuccessHandler logoutSuccessHandler = new TigerLogoutSuccessHandler("/");

    public static final String[] NO_AUTH_LIST={
            "/test",
            "/static/**",
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().formLogin().loginPage("/login").loginProcessingUrl("/login").usernameParameter(
                "username").permitAll()
                //失败处理
                .failureHandler((req, resp, e) -> ResponseUtil.restResponse(resp, HttpStatus.FORBIDDEN, RestResult.error(403, e.getMessage())))
                //成功处理
                .successHandler((req ,resp, e)-> ResponseUtil.restResponse(resp,HttpStatus.OK,RestResult.success(SecurityContextHolder.getContext().getAuthentication().getPrincipal())))
                .permitAll()
                .and().exceptionHandling()
                //请求登录处理，改变默认跳转登录页
                .authenticationEntryPoint((req, resp, e) -> ResponseUtil.restResponse(resp, HttpStatus.UNAUTHORIZED, RestResult.error(401, "请先登录")))
                //没有权限访问
                .accessDeniedHandler((req, resp, e) -> ResponseUtil.restResponse(resp, HttpStatus.FORBIDDEN, RestResult.error(403, "抱歉，你当前的身份无权访问")))
                //设置最大一人同时登陆
                .and().sessionManagement().maximumSessions(1)
                .expiredSessionStrategy(s -> ResponseUtil.restResponse(s.getResponse(), HttpStatus.FORBIDDEN, RestResult.error(499, "您的账号在别的地方登录，当前登录已失效")))
                .and()
                //设置登出
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler).deleteCookies("JSESSIONID").permitAll()
                .and().authorizeRequests().antMatchers(NO_AUTH_LIST).permitAll()
                .and().authorizeRequests().anyRequest().authenticated();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
