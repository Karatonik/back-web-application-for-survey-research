package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.Award;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.payload.response.UserInfo;

import java.util.List;

public interface UserService {

    User getUser(String email);

    User setUser(String email, String password);

    boolean deleteUser(String email);

    List<Survey> getUserSurvey(String email);

    Long getUserPoints(String email);

    List<Award> getUserAwards(String email);

    List<UserInfo> getAllUsers(String adminEmail);

    boolean increasePermissionsForUser(String adminEmail, String userEmail);

    boolean reducePermissionsForUser(String adminEmail, String userEmail);
}
