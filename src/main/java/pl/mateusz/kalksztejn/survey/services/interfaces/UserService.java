package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    boolean confirmation(String key);

    boolean deleteWithKey(String key);

    boolean changePassword(String key , String newPassword);

    User set (String email ,String password);

    User get(String email);

    boolean delete(String email);

    List<Survey> getUserSurvey(String email);
}
