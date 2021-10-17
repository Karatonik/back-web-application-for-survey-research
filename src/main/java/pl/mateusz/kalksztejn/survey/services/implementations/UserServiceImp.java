package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyRepository;
import pl.mateusz.kalksztejn.survey.repositorys.UserRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    UserRepository userRepository;
    SurveyRepository surveyRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository, SurveyRepository surveyRepository) {
        this.userRepository = userRepository;
        this.surveyRepository = surveyRepository;
    }

    @Override
    public boolean confirmation(String key) {
        Optional<User> optionalUser = userRepository.findByUserKey(key);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActivated(true);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteWithKey(String key) {
        Optional<User> optionalUser = userRepository.findByUserKey(key);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            //survey
            surveyRepository.deleteAll(surveyRepository.findAllByOwner(user));
            //todo
            //user
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public User set(String email, String password) {
        return userRepository.save(new User(email, password));
    }

    @Override
    public User get(String email) {
        return userRepository.getById(email);
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
    public boolean changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.getById(email);
        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<Survey> getUserSurvey(String email) {
        return userRepository.getById(email).getUserSurveyList();
    }
}
