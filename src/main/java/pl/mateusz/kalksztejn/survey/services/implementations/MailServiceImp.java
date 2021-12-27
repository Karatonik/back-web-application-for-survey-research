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
    @Value("${reward.mail}")
    private  long rewardMailPoints;
    @Autowired
    public MailServiceImp(JavaMailSender javaMailSender, UserRepository userRepository,AwardRepository awardRepository,
                          SurveyRepository surveyRepository) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
        this.surveyRepository =surveyRepository;
        this.awardRepository = awardRepository;
    }

    @Override
    public void sendConfirmation(String to) throws MessagingException {
        Optional<User> optionalUser = userRepository.findById(to);
        if (optionalUser.isPresent()) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Confirmation");

            User user = optionalUser.get();
            user.getNewKey();
            userRepository.save(user);
            System.out.println(confMessage + user.getUserKey()+ ">Link</a>");

            mimeMessageHelper.setText(confMessage + user.getUserKey()+ "\">Link</a>" , true);
            javaMailSender.send(mimeMessage);
        }
    }

    @Override
    public boolean sendResetPassword(String to) throws MessagingException {
        Optional<User> optionalUser = userRepository.findById(to);
        if (optionalUser.isPresent()) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Reset Password");
            System.out.println("send");
            User user = optionalUser.get();
            user.getNewKey();
            userRepository.save(user);


            mimeMessageHelper.setText(resetMessage + user.getUserKey()+ "\">Link</a>", true);
            javaMailSender.send(mimeMessage);
            return true;
        }
        return  false;
    }

    @Override
    public void sendDelete(String to) throws MessagingException {
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
        if(optionalSurvey.isPresent()){
            Survey survey = optionalSurvey.get();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        respondents.stream().parallel().forEach(respondent -> {
            try {
                mimeMessageHelper.setTo(respondent);
                mimeMessageHelper.setSubject("New Survey waiting for you");
                mimeMessageHelper.setText(invMessage+ survey.getName(), true);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                System.out.println(e.getMessage());
            }
        });
        return true;
        }
        return  false;
    }

    @Override
    public boolean sendRewardEmail(String to) throws MessagingException {

        Optional<User> optionalUser = userRepository.findById(to);
        if(optionalUser.isPresent()){

            User user = optionalUser.get();
            if(user.getPoints()>=rewardMailPoints) {

                Award award = awardRepository.save( new Award("RewardEmail",1000L));
                user.addAward(award);

                user.subtractPoints(rewardMailPoints);
                userRepository.save(user);

                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                mimeMessageHelper.setTo(to);
                mimeMessageHelper.setSubject("Thank-you mail form Survey-Research");
                mimeMessageHelper.setText(rewardMessage, false);
                javaMailSender.send(mimeMessage);


                return true;
            }
        }

        return  false;
    }
}
