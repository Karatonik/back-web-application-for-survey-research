package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User set (String email ,String password);

    User get(String email);

    boolean changePassword(String email ,String oldPassword ,String newPassword);

    List<Survey> getUserSurvey(String email);
}