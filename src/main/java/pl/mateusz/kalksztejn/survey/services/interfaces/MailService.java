package pl.mateusz.kalksztejn.survey.services.interfaces;

import javax.mail.MessagingException;
import java.util.List;

public interface MailService {

    void sendConfirmationMail(String to) throws MessagingException;

    void sendDeleteMail(String to) throws MessagingException;

    boolean sendResetPasswordMail(String to) throws MessagingException;

    void sendMail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException;

    boolean sendMailsWithSurvey(List<String> respondents, Long surveyId) throws MessagingException;

    boolean sendRewardMail(String to) throws MessagingException;
}
