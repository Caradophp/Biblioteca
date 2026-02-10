package com.biblioteca.biblioteca.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(String para, String assunto, String texto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(para);
        message.setSubject(assunto);
        message.setText(texto);

        mailSender.send(message);
    }

    public void enviarEmailHtml(String para, String assunto, String html) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(para);
        helper.setSubject(assunto);
        helper.setText(html, true); // true = HTML

        mailSender.send(message);
    }
}
