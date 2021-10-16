package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.repositorys.UserRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

   private UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User set(String email, String password) {
        return userRepository.save(new User(email,password));
    }

    @Override
    public User get(String email) {
        return userRepository.getById(email);
    }

    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword) {
     User user =  userRepository.getById(email);
     if(user.getPassword().equals(oldPassword)){
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
