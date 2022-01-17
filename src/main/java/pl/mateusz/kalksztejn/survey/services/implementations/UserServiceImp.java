package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Award;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.enums.AccountType;
import pl.mateusz.kalksztejn.survey.models.payload.response.UserInfo;
import pl.mateusz.kalksztejn.survey.repositorys.AwardRepository;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyRepository;
import pl.mateusz.kalksztejn.survey.repositorys.UserRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    UserRepository userRepository;
    SurveyRepository surveyRepository;
    AwardRepository awardRepository;
    PasswordEncoder encoder;


    @Autowired
    public UserServiceImp(UserRepository userRepository, SurveyRepository surveyRepository, AwardRepository awardRepository
            , PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.surveyRepository = surveyRepository;
        this.awardRepository = awardRepository;
        this.encoder = encoder;
    }


    @Override
    public User getUser(String email) {
        return userRepository.getById(email);
    }

    @Override
    public User setUser(String email, String password) {
        return userRepository.save(new User(email, password));
    }

    @Override
    public boolean deleteUser(String email) {
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
    public Long getUserPoints(String email) {
        Optional<User> optionalUser = userRepository.findById(email);
        return optionalUser.map(User::getPoints).orElse(0L);
    }

    @Override
    public List<Award> getUserAwards(String email) {
        Optional<User> optionalUser = userRepository.findById(email);
        return optionalUser.map(User::getAwards).orElseGet(ArrayList::new);
    }

    @Override
    public List<UserInfo> getAllUsers(String adminEmail) {
        Optional<User> optionalUser = userRepository.findById(adminEmail);
        if (optionalUser.isPresent()) {
            if (optionalUser.get().getAccountType().equals(AccountType.admin)) {
                return userRepository.findAll().stream().map(UserInfo::new).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean increasePermissionsForUser(String adminEmail, String userEmail) {
        Optional<User> optionalAdmin = userRepository.findById(adminEmail);
        if (optionalAdmin.isPresent()) {
            User admin = optionalAdmin.get();
            if (admin.getAccountType().equals(AccountType.admin)) {
                User user = userRepository.getById(userEmail);
                user.setAccountType(AccountType.increaseType(user.getAccountType()));
                userRepository.save(user);

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean reducePermissionsForUser(String adminEmail, String userEmail) {
        Optional<User> optionalAdmin = userRepository.findById(adminEmail);
        if (optionalAdmin.isPresent()) {
            User admin = optionalAdmin.get();
            if (admin.getAccountType().equals(AccountType.admin)) {
                User user = userRepository.getById(userEmail);
                user.setAccountType(AccountType.reduceType(user.getAccountType()));
                userRepository.save(user);

                return true;
            }
        }
        return false;
    }
}
