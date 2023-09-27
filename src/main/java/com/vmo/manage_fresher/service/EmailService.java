package com.vmo.manage_fresher.service;

import com.vmo.manage_fresher.entity.Fresher;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
    void scheduleToSendEmailToFresher() throws MessagingException, UnsupportedEncodingException;
    void sendEmailToFresher(Fresher fresher) throws MessagingException, UnsupportedEncodingException;
}
