package pl.mateusz.kalksztejn.survey.integrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mateusz.kalksztejn.survey.SurveyApplication;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SurveyApplication.class)
@TestPropertySource(
        locations = "classpath:application-integrations.properties")
public class UserIntTest {

    @Autowired
    private UserService userService;

    private User user = new User("test@mail.com", "password123456789");

    @Test
    public void setTest() throws Exception {
        assertEquals(userService.setUser(user.getEmail(), user.getPassword()).getEmail(), user.getEmail());
    }

    @Test
    public void getTest() {
        user = userService.setUser(user.getEmail(), user.getPassword());
        assertEquals(userService.getUser(user.getEmail()).getEmail(), user.getEmail());
    }

    @Test
    public void deleteTest() {
        user = userService.setUser(user.getEmail(), user.getPassword());
        assertTrue(userService.deleteUser(user.getEmail()));
    }

    @Test
    public void getPointsTest() {
        user = userService.setUser(user.getEmail(), user.getPassword());
        Long points = userService.getUserPoints(user.getEmail());
        assertEquals(points.toString(), "0");

    }
}
