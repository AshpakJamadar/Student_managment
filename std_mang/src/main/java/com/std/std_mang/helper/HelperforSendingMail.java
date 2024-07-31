package com.std.std_mang.helper;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.std.std_mang.dto.MyUser;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class HelperforSendingMail {

    @Autowired
    JavaMailSender mailSender;

    public void sendEmail(MyUser myUser){
         MimeMessage mimeMessage=mailSender.createMimeMessage();
         MimeMessageHelper helper=new MimeMessageHelper(mimeMessage);
         try{
          helper.setFrom("ashpakmjamadar01@gmail.com","Student Managment");
          helper.setTo(myUser.getEmail());
          helper.setSubject("OTP verification");
          helper.setText("<h1> Hello ,"+myUser.getName()+"Your Otp is:" +myUser.getOtp()+"</h1>"+true);

         }catch(MessagingException | UnsupportedEncodingException e){
              e.printStackTrace();
         }
         
    mailSender.send(mimeMessage);
    }
}
