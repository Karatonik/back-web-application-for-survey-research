package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Award;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.repositorys.AwardRepository;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyRepository;
import pl.mateusz.kalksztejn.survey.repositorys.UserRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;

@Service
public class MailServiceImp implements MailService {

    JavaMailSender javaMailSender;

    UserRepository userRepository;
    AwardRepository awardRepository;

    SurveyRepository surveyRepository;
    @Value("${my.confMessage}")
    private String confMessage;
    @Value("${my.resetMessage}")
    private String resetMessage;
    @Value("${my.deleteMessage}")
    private String deleteMessage;
    @Value("${my.invMessage}")
    private String invMessage;
    @Value("${my.rewardMessage}")
    private String rewardMessage;

    @Autowired
    public MailServiceImp(JavaMailSender javaMailSender, UserRepository userRepository, AwardRepository awardRepository,
                          SurveyRepository surveyRepository) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.surveyRepository = surveyRepository;
        this.awardRepository = awardRepository;
    }

    @Override
    public void sendConfirmationMail(String to) throws MessagingException {
        Optional<User> optionalUser = userRepository.findById(to);
        if (optionalUser.isPresent()) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Confirmation");

            User user = optionalUser.get();
            user.getNewKey();
            userRepository.save(user);
            //activation link in the console
            System.out.println(confMessage + user.getUserKey() + ">Link</a>");

            mimeMessageHelper.setText(confMessage + user.getUserKey() + "\">Link</a>", true);
            javaMailSender.send(mimeMessage);
        }
    }

    @Override
    public boolean sendResetPasswordMail(String to) throws MessagingException {
        Optional<User> optionalUser = userRepository.findById(to);
        if (optionalUser.isPresent()) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Reset Password");
            User user = optionalUser.get();
            user.getNewKey();
            userRepository.save(user);


            mimeMessageHelper.setText(resetMessage + user.getUserKey() + "\">Link</a>", true);
            javaMailSender.send(mimeMessage);
            return true;
        }
        return false;
    }

    @Override
    public void sendDeleteMail(String to) throws MessagingException {
        Optional<User> optionalUser = userRepository.findById(to);
        if (optionalUser.isPresent()) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Delete Account");

            User user = optionalUser.get();
            user.getNewKey();
            userRepository.save(user);

            mimeMessageHelper.setText(deleteMessage + user.getUserKey(), true);
            javaMailSender.send(mimeMessage);
        }
    }

    @Override
    public void sendMail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
    }

    @Override
    public boolean sendMailsWithSurvey(List<String> respondents, Long surveyId) throws MessagingException {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            respondents.stream().parallel().forEach(respondent -> {
                try {
                    mimeMessageHelper.setTo(respondent);
                    mimeMessageHelper.setSubject("New Survey waiting for you");
                    mimeMessageHelper.setText(invMessage + survey.getName(), true);
                    javaMailSender.send(mimeMessage);
                } catch (MessagingException e) {
                    System.out.println(e.getMessage());
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public boolean sendRewardMail(String to, Award award) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(String.format("you purchased %s on the site", award.getName()));
        mimeMessageHelper.setText(String.format(rewardMessage, award.getName(), award.getCost(), award.getLocalDateTime()), false);
        javaMailSender.send(mimeMessage);


        return true;

    }


}
