package pl.mateusz.kalksztejn.survey.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.mateusz.kalksztejn.survey.controllers.UserController;
import pl.mateusz.kalksztejn.survey.models.Award;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyDTO;
import pl.mateusz.kalksztejn.survey.models.enums.AccountType;
import pl.mateusz.kalksztejn.survey.models.payload.response.UserInfo;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserUnitTests {


    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    User user = new User("test@wp.pl", "password123");



    @Test
    void getUserSurvey_shouldReturnEmptyList() {
        ResponseEntity<List<SurveyDTO>> response = userController.getUserSurvey(user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new ArrayList<>()));
    }

    @Test
    void getUserSurvey_shouldReturnListWithObject() {
        List<Survey> surveys = new ArrayList<>();
        when(userService.getUserSurvey(anyString())).thenReturn(surveys);

        ResponseEntity<List<SurveyDTO>> response = userController.getUserSurvey(user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(surveys.stream().map(SurveyDTO::new).collect(Collectors.toList())));
    }

    @Test
    void getPoints_shouldReturnZero() {
        ResponseEntity<Long> response = userController.getPoints(user.getEmail());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(0L));
    }

    @Test
    void getPoints_shouldReturnUserPoints() {
        user.setPoints(10);

        when(userService.getUserPoints(anyString())).thenReturn(user.getPoints());

        ResponseEntity<Long> response = userController.getPoints(user.getEmail());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(user.getPoints()));
    }

    @Test
    void getUserAwards_shouldReturnEmptyList() {
        ResponseEntity<List<Award>> response = userController.getUserAwards(user.getEmail());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new ArrayList<>()));
    }

    @Test
    void getUserAwards_shouldReturnListWithObject() {
        Award award = new Award("test", 100L);
        List<Award> awards = new ArrayList<>();
        awards.add(award);

        when(userService.getUserAwards(anyString())).thenReturn(awards);

        ResponseEntity<List<Award>> response = userController.getUserAwards(user.getEmail());
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(awards));
    }

    @Test
    void getAllUsers_shouldReturnEmptyList() {
        user.setAccountType(AccountType.consumer);

        ResponseEntity<List<UserInfo>> response = userController.getAllUsers(user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new ArrayList<>()));
    }

    @Test
    void getAllUsers_shouldReturnListWithObject() {
        user.setAccountType(AccountType.admin);
        List<UserInfo> userInfoList = new ArrayList<>();
        userInfoList.add(new UserInfo(user));

        when(userService.getAllUsers(user.getEmail())).thenReturn(userInfoList);

        ResponseEntity<List<UserInfo>> response = userController.getAllUsers(user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(userInfoList));
    }

    @Test
    void increasePermissionsForUser_shouldReturnFalse() {
        user.setAccountType(AccountType.consumer);

        ResponseEntity<Boolean> response = userController.increasePermissionsForUser(user.getEmail(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(false));
    }

    @Test
    void increasePermissionsForUser_shouldReturnTrue() {
        user.setAccountType(AccountType.consumer);
        User admin = new User("admin@wp.pl", "password123");
        admin.setAccountType(AccountType.admin);

        when(userService.increasePermissionsForUser(anyString(), anyString())).thenReturn(true);

        ResponseEntity<Boolean> response = userController.increasePermissionsForUser(admin.getEmail(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(true));
    }

    @Test
    void reducePermissionsForUser_shouldReturnFalse() {
        user.setAccountType(AccountType.consumer);

        ResponseEntity<Boolean> response = userController.reducePermissionsForUser(user.getEmail(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(false));
    }

    @Test
    void reducePermissionsForUser_shouldReturnTrue() {
        user.setAccountType(AccountType.creator);
        User admin = new User("admin@wp.pl", "password123");
        admin.setAccountType(AccountType.admin);

        when(userService.reducePermissionsForUser(anyString(), anyString())).thenReturn(true);

        ResponseEntity<Boolean> response = userController.reducePermissionsForUser(admin.getEmail(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(true));
    }
}
