package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.Award;

import javax.mail.MessagingException;
import java.util.List;

public interface MailService {

    void sendConfirmationMail(String to);

    void sendDeleteMail(String to);

    boolean sendResetPasswordMail(String to);

    void sendMail(String to, String subject, String text, boolean isHtmlContent);

    boolean sendMailsWithSurvey(List<String> respondents, Long surveyId);

    boolean sendRewardMail(String to, Award award);
}
