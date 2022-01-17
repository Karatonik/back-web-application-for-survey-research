package pl.mateusz.kalksztejn.survey.unitTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pl.mateusz.kalksztejn.survey.SurveyApplication;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.dto.UserDTO;
import pl.mateusz.kalksztejn.survey.services.interfaces.MailService;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import java.util.ArrayList;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SurveyApplication.class})
@SpringBootTest
public class UserUnitTest {
    private final String apiPath = "/api/user";
    private final User user = new User("test@mail.com", "password123456789");
    private final UserDTO userDTO = new UserDTO(user);
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;
    @MockBean
    private MailService mailService;

    @Test
    public void getUserSurveyTest() throws Exception {
        when(userService.getUserSurvey(anyString())).thenReturn(new ArrayList<>());

        mvc.perform(get(apiPath + "/" + user.getEmail())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getPointsTest() throws Exception {
        when(userService.getUserPoints(anyString())).thenReturn(1L);

        mvc.perform(get(apiPath + "/p/" + user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("1")));
    }


}
