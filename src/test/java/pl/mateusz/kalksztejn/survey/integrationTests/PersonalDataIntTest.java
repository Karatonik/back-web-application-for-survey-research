package pl.mateusz.kalksztejn.survey.integrationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mateusz.kalksztejn.survey.SurveyApplication;
import pl.mateusz.kalksztejn.survey.models.PersonalData;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;
import pl.mateusz.kalksztejn.survey.repositorys.PersonalDataRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.PersonalDataService;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SurveyApplication.class)
@TestPropertySource(
        locations = "classpath:application-integrations.properties")
public class PersonalDataIntTest {

    @Autowired
    PersonalDataService personalDataService;
    @Autowired
    PersonalDataRepository personalDataRepository;
    @Autowired
    UserService userService;

    private User user = new User("test@mail.com", "test123456");

    private PersonalData personalData = new PersonalData(1000000L, 18L, Gender.male, 100000L,
            100000L, 3000.0, Education.engineer, LaborSector.unemployment,
            MaritalStatus.single, user);


    @Before
    public void init() {
        user = userService.setUser(user.getEmail(), user.getPassword());
        personalData.setUser(user);

    }

    @After
    public void destroy() {
        PersonalData personalData1 = personalDataService.getPersonalDataByUser(user.getEmail());
        if (personalData1 != null) {
            personalDataRepository.delete(personalData1);
        }
        User user1 = userService.getUser("test@mail.com");
        if (user1 != null) {
            userService.deleteUser("test@mail.com");
        }
    }

    @Test
    public void setTest() {
        assertEquals(personalDataService.setPersonalData(personalData), personalData);
    }

    @Test
    public void getTest() {
        personalData = personalDataService.setPersonalData(personalData);
        assertEquals(personalDataService.getPersonalData(personalData.getId()).getAge(), personalData.getAge());
    }
}
