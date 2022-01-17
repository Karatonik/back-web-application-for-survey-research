package pl.mateusz.kalksztejn.survey.intTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
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
import pl.mateusz.kalksztejn.survey.models.Reward;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.services.interfaces.RewardService;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SurveyApplication.class})
@SpringBootTest
public class RewardInitTests {
    private final String apiPath = "/api/rew";
    private final Reward reward = new Reward("test", 10000L, 1000L);
    private final User user = new User("test@mail.com", "test123456");
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RewardService rewardService;

    @Test
    public void setReward_responseShouldContainName() throws Exception {
        when(rewardService.setReward(any(Reward.class))).thenReturn(reward);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reward);

        mvc.perform(post(apiPath).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(reward.getName())));
    }

    @Test
    public void getRewardForUser_responseShouldContainTrue() throws Exception {
        when(rewardService.getRewardForUser(anyString(), anyString())).thenReturn(true);

        mvc.perform(get(apiPath + String.format("/%s/%s", reward.getName(), user.getEmail()))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void getRewardForUser_responseShouldContainFalse() throws Exception {
        mvc.perform(get(apiPath + String.format("/%s/%s", reward.getName(), user.getEmail()))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("false")));
    }

    @Test
    public void getAllRewards_expectStatusOk() throws Exception {
        mvc.perform(get(apiPath).contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteReward_responseShouldContainFalse() throws Exception {
        mvc.perform(delete(apiPath + "/" + reward.getName())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("false")));
    }

    @Test
    public void deleteReward_responseShouldContainTrue() throws Exception {
        when(rewardService.deleteReward(anyString())).thenReturn(true);

        mvc.perform(delete(apiPath + "/" + reward.getName())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("true")));
    }

}
