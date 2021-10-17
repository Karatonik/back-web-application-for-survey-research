package pl.mateusz.kalksztejn.survey.services.interfaces;

import javax.mail.MessagingException;
import java.util.List;

public interface MailService {

    void sendConfirmation(String to) throws MessagingException;

    void sendDelete(String to) throws MessagingException;

    boolean sendResetPassword(String to) throws MessagingException;

    void sendMail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException;

    void sendMailsWithSurvey(List<String> respondents, Long surveyId) throws MessagingException;
}
