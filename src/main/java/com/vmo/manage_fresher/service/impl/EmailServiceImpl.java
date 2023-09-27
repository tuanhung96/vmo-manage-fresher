package com.vmo.manage_fresher.service.impl;

import com.vmo.manage_fresher.dao.FresherRepository;
import com.vmo.manage_fresher.entity.Fresher;
import com.vmo.manage_fresher.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String FROM_ADDRESS = "tuanhung.hn96@gmail.com";
    private static final String SENDER_NAME = "Center";
    private static final String SUBJECT = "Score Notification";
    private JavaMailSender mailSender;
    private FresherRepository fresherRepository;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, FresherRepository fresherRepository) {
        this.mailSender = mailSender;
        this.fresherRepository = fresherRepository;
    }


    @Scheduled(cron = "0 0 0 1 * ?")
    public void scheduleToSendEmailToFresher() throws MessagingException, UnsupportedEncodingException {
        List<Fresher> fresherList = fresherRepository.findAll();
        for (Fresher fresher: fresherList) {
            sendEmailToFresher(fresher);
        }
    }

    public void sendEmailToFresher(Fresher fresher) throws MessagingException, UnsupportedEncodingException {

        String toAddress = fresher.getEmail();
        String averageScore = String.format("%.2f", (fresher.getScore_1()+fresher.getScore_2()+fresher.getScore_3())/3);
        String content = "Dear " + fresher.getName() + ",<br>"
                + "Your score: <br>"
                + "Score 1: " + fresher.getScore_1() + ", "
                + "Score 2: " + fresher.getScore_2() + ", "
                + "Score 3: " + fresher.getScore_3() + ".<br>"
                + "Average Score: " + averageScore + ".<br>"
//                + "<h3><a href=\""+ "\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you!<br>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(FROM_ADDRESS, SENDER_NAME);
        helper.setTo(toAddress);
        helper.setSubject(SUBJECT);
        helper.setText(content, true);

        mailSender.send(message);
    }
}
