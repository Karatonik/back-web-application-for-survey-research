package pl.mateusz.kalksztejn.survey.services.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface UserService {

    User set (String email ,String password);

    User get(String email);

    boolean delete(String email);

    List<Survey> getUserSurvey(String email);

    Long getPoints(String email);

    ResponseEntity<Resource> getMascot(String email) throws IOException;
}
