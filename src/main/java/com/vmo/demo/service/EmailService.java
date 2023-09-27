package com.vmo.demo.service;

import com.vmo.demo.entity.Fresher;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
    void scheduleToSendEmailToFresher() throws MessagingException, UnsupportedEncodingException;
    void sendEmailToFresher(Fresher fresher) throws MessagingException, UnsupportedEncodingException;
}
