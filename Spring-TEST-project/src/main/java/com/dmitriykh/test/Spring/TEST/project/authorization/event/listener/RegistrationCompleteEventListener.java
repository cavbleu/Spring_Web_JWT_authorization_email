package com.dmitriykh.test.Spring.TEST.project.authorization.event.listener;

import com.dmitriykh.test.Spring.TEST.project.authorization.event.RegistrationCompleteEvent;
import com.dmitriykh.test.Spring.TEST.project.authorization.user.User;
import com.dmitriykh.test.Spring.TEST.project.authorization.token.VerificationTokenServiceIMPL;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

    private final VerificationTokenServiceIMPL tokenService;
    private final JavaMailSender mailSender;
    private User user;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        user = event.getUser();
        String vToken = UUID.randomUUID().toString();
        tokenService.saveVerificationTokenForUser(user, vToken);
        String url = event.getConfirmationUrl()+"/registration/verifyEmail?token="+vToken;
        try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Проверка Email";
        String senderName = "Сервис портала регистрации пользователей";
        String mailContent = "<p> Здравствуйте, "+ user.getFirstName()+ ", </p>"+
                "<p>Благодарим вас за регистрацию,"+"" +
                "Пожалуйста, перейдите по ссылке ниже, чтобы завершить регистрацию.</p>"+
                "<a href=\"" +url+ "\">Нажимая по ссылке, вы подтверждаете регистрацию</a>"+
                "<p> Спасибо <br> \n" +
                "Сервис портала регистрации пользователей";
        emailMessage(subject, senderName, mailContent, mailSender, user);
    }

    public void sendPasswordResetVerificationEmail(String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Проверка сброса пароля";
        String senderName = "Сервис портала проверки пользователей";
        String mailContent = "<p> Здравствуйте, "+ user.getFirstName()+ ", </p>"+
                "<p><b>Недавно вы запросили сброс своего пароля,</b>"+"" +
                "Пожалуйста, перейдите по ссылке ниже, чтобы завершить действие.</p>"+
                "<a href=\"" +url+ "\">Сброс пароля</a>"+
                "<p>Сервис портала регистрации пользователей";
        emailMessage(subject, senderName, mailContent, mailSender, user);
    }

    private static void emailMessage(String subject, String senderName,
                                     String mailContent, JavaMailSender mailSender, User theUser)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("your_email@gmail.com", senderName);
        messageHelper.setTo(theUser.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
