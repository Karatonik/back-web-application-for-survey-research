package pl.mateusz.kalksztejn.survey.integrationTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mateusz.kalksztejn.survey.SurveyApplication;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyFilter;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyFilterService;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyService;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SurveyApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrations.properties")
public class SurveyFilterIntTest {


    @Autowired
    SurveyFilterService surveyFilterService;

    @Autowired
    UserService userService;

    @Autowired
    SurveyService surveyService;

    //utwórz survey :/
    private User user = new User("test@mail.com", "password123456789");
    private Survey survey = new Survey(1L, "Test survey", user, new ArrayList<>(), new ArrayList<>());
    private SurveyFilter surveyFilter = new SurveyFilter(1L, survey, 18L, 99L, new ArrayList<>(),
            100000L, 1000000L, 10000L, 1000000L,
            0.0, 90000.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

    @Before
    public void init() {
        user = userService.set(user.getEmail(), user.getPassword());
        survey = surveyService.set(survey.getName(), user.getEmail());
        surveyFilter.setSurvey(survey);
    }


    @Test
    public void setTest() {
        assertEquals(surveyFilterService.set(surveyFilter).getAgeMax(), surveyFilter.getAgeMax());
    }

    @Test
    public void editTest() {
        surveyFilter = surveyFilterService.set(surveyFilter);
        surveyFilter.setAgeMax(22L);

        assertEquals(surveyFilterService.edit(surveyFilter).getAgeMax(), surveyFilter.getAgeMax());

    }

    @Test
    public void deleteTest() throws Exception {
        surveyFilter = surveyFilterService.set(surveyFilter);
        assertTrue(surveyFilterService.delete(surveyFilter.getId(), user.getEmail()));
    }
}
