package com.babble.api.service;

import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    JavaMailSender emailSender;

    public static final String ePw = createKey();

    private MimeMessage createMessage(String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("Babble회원가입 이메일 인증");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 Babble입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("Babble@babble.com","Babble"));//보내는 사람

        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }
    @Override
    public String sendSimpleMessage(String to)throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }

    // 방이 생성되면 해당 해시테그를 가진 유젇르에게 이메일을 보낸다.
    private MimeMessage createHashTagMessage(String to, String hashtag, Long roomId) throws Exception{
        System.out.println("보내는 대상 : "+ to);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("[Babble] 관심 주제 대화방이 생성되었습니다.");//제목
        // 포트번호 변경 시 링크 수정
        String linkUrl = "https://i5a308.p.ssafy.io/conferences/" + roomId;

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 Babble입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<h4>회원님이 등록하신 관심 주제 <strong>[" + hashtag +"]</strong>에 대한 방이 생성되었습니다.</h4>";
        msgg+= "<h4><a href=" + linkUrl +">링크</a>를 눌러 확인해보세요!</h4>";
        msgg+= "<br>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("babble0802@gmail.com","Babble"));//보내는 사람

        return message;
    }

    @Override
    public void sendHashtagMessage(String to, String hashtag, Long roomId) throws Exception {
        MimeMessage message = createHashTagMessage(to, hashtag, roomId);
        try{ //예외처리
            emailSender.send(message);
        } catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String sendTempPassword(String email) throws Exception {
        // 임시 비밀번호 생성
        String tempPassword = "";
        for (int i = 0; i < 12; i++) {
            tempPassword += (char) ((Math.random() * 26) + 97);
        }
        MimeMessage message = createTempPasswordMessage(email, tempPassword);
        try{  //예외처리
            emailSender.send(message);
        } catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

        return tempPassword;
    }

    private MimeMessage createTempPasswordMessage(String to, String tempPassword) throws Exception{
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("[Babble] 임시 비밀번호가 발급되었습니다.");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 Babble입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>회원님 계정에 임시 비밀번호가 발급되었습니다.</p>";
        msgg+= "<p>아래 비밀번호로 로그인 후 비밀번호를 변경해주세요</p><br>";
        msgg+= "<h4>" + tempPassword +"</h4>";
        msgg+= "<br>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("babble0802@gmail.com","Babble"));//보내는 사람

        return message;
    }

}
