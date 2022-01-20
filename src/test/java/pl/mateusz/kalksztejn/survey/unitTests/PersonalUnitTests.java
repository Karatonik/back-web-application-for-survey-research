package pl.mateusz.kalksztejn.survey.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.mateusz.kalksztejn.survey.controllers.PersonalDataController;
import pl.mateusz.kalksztejn.survey.models.PersonalData;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.dto.PersonalDataDTO;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;
import pl.mateusz.kalksztejn.survey.models.payload.response.SurveyInfo;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.PersonalDataService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonalUnitTests {


    User user = new User("test@wp.pl", "password123");
    private final PersonalData personalData = new PersonalData(1L, 18L, Gender.male, 100000L,
            100000L, 3000.0, Education.engineer, LaborSector.unemployment,
            MaritalStatus.single, user);
    private final PersonalDataDTO personalDataDTO = new PersonalDataDTO(personalData);
    @Mock
    private PersonalDataService personalDataService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private PersonalDataController personalDataController;

    @Test
    void setPersonalData_createPersonalData() {
        when(personalDataService.setPersonalData(any(PersonalData.class))).thenReturn(personalData);
        when(modelMapper.personalDataMapper(any(PersonalDataDTO.class))).thenReturn(personalData);

        ResponseEntity<PersonalDataDTO> response = personalDataController
                .setPersonalData(new PersonalDataDTO(personalData));

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(personalDataDTO));
    }

    @Test
    void editPersonalData_editPersonalData() {
        PersonalData personalData = new PersonalData();
        personalData.setId(1L);
        personalData.setUser(user);

        when(personalDataService.editPersonalData(any(PersonalData.class))).thenReturn(personalData);
        when(modelMapper.personalDataMapper(any(PersonalDataDTO.class))).thenReturn(personalData);


        ResponseEntity<PersonalDataDTO> response = personalDataController
                .editPersonalData(new PersonalDataDTO(personalData));

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), is(personalData.getId()));
    }

    @Test
    void getPersonalData_returnPersonalData() {
        PersonalData personalData = new PersonalData();
        personalData.setId(1L);
        personalData.setUser(user);

        when(personalDataService.getPersonalData(anyLong())).thenReturn(personalData);

        ResponseEntity<PersonalDataDTO> response = personalDataController
                .getPersonalData(personalData.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), is(personalData.getId()));
    }

    @Test
    void getPersonalDataByUser_returnPersonalData() {
        PersonalData personalData = new PersonalData();
        personalData.setId(1L);
        personalData.setUser(user);

        when(personalDataService.getPersonalDataByUser(anyString())).thenReturn(personalData);

        ResponseEntity<PersonalDataDTO> response = personalDataController
                .getPersonalDataByUser(user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getId(), is(personalData.getId()));
    }

    @Test
    void getSurveys_returnEmptyList() {
        PersonalData personalData = new PersonalData();
        personalData.setId(1L);
        personalData.setUser(user);

        ResponseEntity<List<SurveyInfo>> response = personalDataController
                .getSurveys(1L, user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new ArrayList<>()));
    }

    @Test
    void getSurveys_returnListWithOneObject() {
        Survey survey = new Survey("Test", user);
        survey.setId(1L);
        List<SurveyInfo> surveyInfos = new ArrayList<>();
        surveyInfos.add(new SurveyInfo(survey));

        when(personalDataService.getSurveys(anyLong(), anyString())).thenReturn(surveyInfos);

        ResponseEntity<List<SurveyInfo>> response = personalDataController
                .getSurveys(1L, user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(surveyInfos));
    }

}

