package pl.mateusz.kalksztejn.survey.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.mateusz.kalksztejn.survey.controllers.SurveyController;
import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.dto.ResultDTO;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyDTO;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SurveyUnitTests {


    private final User user = new User("test@mail.com", "password123456789");
    private final Survey survey = new Survey(1L, "Test survey", user, new ArrayList<>(), new ArrayList<>());
    private final Query query = new Query(1L, 1L, "test", false
            , 0L, 1L, new ArrayList<>());
    @Mock
    private SurveyService surveyService;
    @InjectMocks
    private SurveyController surveyController;

    @Test
    void getSurvey_shouldReturnSurvey() {
        when(surveyService.getSurvey(anyLong(), anyString())).thenReturn(survey);

        ResponseEntity<SurveyDTO> response = surveyController.getSurvey(survey.getId(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new SurveyDTO(survey)));
    }

    @Test
    void setSurvey_shouldCreateSurvey() {
        when(surveyService.setSurvey(anyString(), anyString())).thenReturn(survey);

        ResponseEntity<SurveyDTO> response = surveyController.setSurvey(survey.getName(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new SurveyDTO(survey)));
    }

    @Test
    void deleteSurvey_shouldReturnTrue() {
        when(surveyService.deleteSurvey(anyLong(), anyString())).thenReturn(true);

        ResponseEntity<Boolean> response = surveyController.deleteSurvey(survey.getId(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(true));
    }

    @Test
    void deleteSurvey_shouldReturnFalse() {
        ResponseEntity<Boolean> response = surveyController.deleteSurvey(survey.getId(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(false));
    }

    @Test
    void getResults_shouldReturnEmptyList() {
        ResponseEntity<List<List<ResultDTO>>> response = surveyController.getResults(survey.getId(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new ArrayList<>()));
    }

    @Test
    void getResults_shouldReturnListWithObject() {
        List<ResultDTO> resultDTOS = new ArrayList<>();
        ResultDTO resultDTO = new ResultDTO(1, "test");
        resultDTOS.add(resultDTO);
        List<List<ResultDTO>> resultDTOSList = new ArrayList<>();
        resultDTOSList.add(resultDTOS);

        when(surveyService.getSurveyResults(anyLong(), anyString())).thenReturn(resultDTOSList);

        ResponseEntity<List<List<ResultDTO>>> response = surveyController.getResults(survey.getId(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(resultDTOSList));
    }

    @Test
    void getQueries_shouldReturnEmptyList() {
        ResponseEntity<List<Query>> response = surveyController.getQueries(survey.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new ArrayList<>()));
    }

    @Test
    void getQueries_shouldReturnListWithObject() {
        List<Query> queries = new ArrayList<>();
        queries.add(query);

        when(surveyService.getQueries(anyLong())).thenReturn(queries);
        ResponseEntity<List<Query>> response = surveyController.getQueries(survey.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(queries));
    }

    @Test
    void getSurveyAllByEmail_shouldReturnEmptyList() {
        ResponseEntity<List<SurveyDTO>> response = surveyController.getAllByEmail(user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new ArrayList<>()));
    }

    @Test
    void getSurveyAllByEmail_shouldReturnListWithObject() {
        List<SurveyDTO> surveyDTOList = new ArrayList<>();
        surveyDTOList.add(new SurveyDTO(survey));

        List<Survey> surveys = new ArrayList<>();
        surveys.add(survey);

        when(surveyService.getAllByEmail(anyString())).thenReturn(surveys);

        ResponseEntity<List<SurveyDTO>> response = surveyController.getAllByEmail(user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(surveyDTOList));
    }

    @Test
    void setQueries_shouldReturnEmptyList() {
        ResponseEntity<List<Query>> response = surveyController.getQueries(survey.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(new ArrayList<>()));
    }

    @Test
    void setQueries_shouldReturnListWithObject() {
        List<Query> queries = new ArrayList<>();
        queries.add(query);

        when(surveyService.getQueries(anyLong())).thenReturn(queries);

        ResponseEntity<List<Query>> response = surveyController.getQueries(survey.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(queries));
    }
}

