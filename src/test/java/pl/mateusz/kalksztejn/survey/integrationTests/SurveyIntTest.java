package pl.mateusz.kalksztejn.survey.integrationTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mateusz.kalksztejn.survey.SurveyApplication;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyService;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SurveyApplication.class)
@TestPropertySource(
        locations = "classpath:application-integrations.properties")
public class SurveyIntTest {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private UserService userService;

    private User user = new User("test@mail.com", "password123456789");
    private Survey survey = new Survey(1L, "Test survey", user, new ArrayList<>(), new ArrayList<>());

    @Before
    public void init() {
        user = userService.setUser(user.getEmail(), user.getPassword());
        survey.setOwner(user);
    }

    @Test
    public void setTest() {
        assertEquals(surveyService.setSurvey(survey.getName(), user.getEmail()).getName(), survey.getName());
    }

    @Test
    public void getTest() {
        survey = surveyService.setSurvey(survey.getName(), user.getEmail());

        assertEquals(surveyService.getSurvey(survey.getId(), user.getEmail()).getId(), survey.getId());
    }
}
