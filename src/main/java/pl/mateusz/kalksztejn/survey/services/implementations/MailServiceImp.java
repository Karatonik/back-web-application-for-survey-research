package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.User;
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
    @Value("${my.confMessage}")
    private String confMessage;
    @Value("${my.deleteMessage}")
    private String deleteMessage;
    @Autowired
    public MailServiceImp(JavaMailSender javaMailSender, UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.userRepository = userRepository;
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

            mimeMessageHelper.setText(confMessage + user.getUserKey(), true);
            javaMailSender.send(mimeMessage);
        }
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
    public void sendMailsWithSurvey(List<String> respondents, Long surveyId) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        respondents.stream().parallel().forEach(respondent -> {
            try {
                mimeMessageHelper.setTo(respondent);
                mimeMessageHelper.setSubject("New Survey for you");//todo
                mimeMessageHelper.setText("text", true);//todo
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });
    }
}
