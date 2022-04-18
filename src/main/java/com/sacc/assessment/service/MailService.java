/*
package com.sacc.assessment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

*/
/**
 * Created by 林夕
 * Date 2021/3/12 15:19
 *//*

@Service
public class MailService {

    @Resource
    private JavaMailSenderImpl sender;

    @Value(value = "${spring.mail.username}")
    private String from;

    */
/*public void sendMail(String mail, String text) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(mail);
            messageHelper.setSubject("忘记密码");

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(text,"text/html;charset=UTF-8");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        sender.send(message);
    }*//*


    */
/**
     * 发送邮件方法
     * @param mail 发送地址
     * @param text 发送内容
     *//*

    public void sendMail(String subject, String mail, String text){
        SimpleMailMessage passWordResetEmail = new SimpleMailMessage();
        passWordResetEmail.setFrom(from);
        passWordResetEmail.setTo(mail);
        passWordResetEmail.setSubject(subject);
        passWordResetEmail.setText(text);
        sender.send(passWordResetEmail);
    }
}
*/
