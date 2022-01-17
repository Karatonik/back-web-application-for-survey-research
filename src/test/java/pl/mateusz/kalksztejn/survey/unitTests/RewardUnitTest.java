package pl.mateusz.kalksztejn.survey.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pl.mateusz.kalksztejn.survey.controllers.RewardController;
import pl.mateusz.kalksztejn.survey.models.Reward;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.services.interfaces.RewardService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RewardUnitTest {

    Reward reward = new Reward("test", 10000L, 1000L);
    User user = new User("test@wp.pl", "password123");
    @Mock
    private RewardService rewardService;
    @InjectMocks
    private RewardController rewardController;

    @Test
    void setReward_shouldCreateReward() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        when(rewardService.setReward(any(Reward.class))).thenReturn(reward);
        ResponseEntity<Reward> response = rewardController.setReward(reward);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(reward));
    }

    @Test
    void deleteReward_shouldDeleteReward() {
        when(rewardService.deleteReward(anyString())).thenReturn(true);

        ResponseEntity<Boolean> response = rewardController.deleteReward(reward.getName());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(true));
    }

    @Test
    void deleteReward_shouldNotFoundReward() {
        ResponseEntity<Boolean> response = rewardController.deleteReward(reward.getName());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(false));
    }

    @Test
    void getAllRewards_shouldReturnEmptyList() {
        ResponseEntity<List<Reward>> response = rewardController.getAllRewards();

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new ArrayList<>()));
    }

    @Test
    void getAllRewards_shouldReturnListWithOneValue() {
        List<Reward> rewards = new ArrayList<>();
        rewards.add(reward);

        when(rewardService.getAllReward()).thenReturn(rewards);

        ResponseEntity<List<Reward>> response = rewardController.getAllRewards();
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(rewards));
    }

    @Test
    void getRewardForUser_shouldNotFoundRewardAndUser() {
        ResponseEntity<Boolean> response = rewardController.getRewardForUser(reward.getName(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(false));
    }

    @Test
    void getRewardForUser_shouldReturnTrue() {
        when(rewardService.getRewardForUser(anyString(), anyString())).thenReturn(true);

        ResponseEntity<Boolean> response = rewardController.getRewardForUser(reward.getName(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(true));
    }
}
