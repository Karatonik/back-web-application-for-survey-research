package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Award;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.repositorys.AwardRepository;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyRepository;
import pl.mateusz.kalksztejn.survey.repositorys.UserRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    UserRepository userRepository;
    SurveyRepository surveyRepository;
    AwardRepository awardRepository;
    PasswordEncoder encoder;


    @Value("${reward.mascot}")
    private  long rewardMascotPoints;
    @Value("${reward.path}")
    private String path;

    @Autowired
    public UserServiceImp(UserRepository userRepository, SurveyRepository surveyRepository,AwardRepository awardRepository
            , PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.surveyRepository = surveyRepository;
        this.awardRepository =awardRepository;
        this.encoder = encoder;
    }


    @Override
    public User get(String email) {
        return userRepository.getById(email);
    }

    @Override
    public User set(String email, String password) {
        return userRepository.save(new User(email,password));
    }

    @Override
    public boolean delete(String email) {
        Optional<User> optionalUser = userRepository.findById(email);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return true;
        }
        return false;
    }


    @Override
    public List<Survey> getUserSurvey(String email) {
        return userRepository.getById(email).getUserSurveyList();
    }

    @Override
    public Long getPoints(String email) {
        Optional<User> optionalUser = userRepository.findById(email);
        return optionalUser.map(User::getPoints).orElse(0L);
    }

    @Override
    public ResponseEntity<Resource> getMascot(String email) throws IOException {
        Optional<User> optionalUser = userRepository.findById(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPoints() > rewardMascotPoints) {

                Award award = awardRepository.save( new Award("Mascot",1000000L));
                user.addAward(award);
                user.subtractPoints(rewardMascotPoints);
                userRepository.save(user);
                final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                        path
                )));
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentLength(inputStream.contentLength())
                        .body(inputStream);

            }
        }
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(null);
    }

    @Override
    public List<Award> getUserAwards(String email) {
        Optional<User> optionalUser = userRepository.findById(email);
        return optionalUser.map(User::getAwards).orElseGet(ArrayList::new);
    }
}
