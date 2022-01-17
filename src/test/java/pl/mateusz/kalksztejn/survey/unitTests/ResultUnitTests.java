package pl.mateusz.kalksztejn.survey.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.mateusz.kalksztejn.survey.controllers.SurveyResultController;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyResult;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyResultDTO;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyResultService;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResultUnitTests {

    private final User user = new User("test@mail.com", "password123456789");
    private final SurveyResult surveyResult = new SurveyResult(1L, user, new ArrayList<>());
    private final SurveyResultDTO surveyResultDTO = new SurveyResultDTO(surveyResult);

    @Mock
    private SurveyResultService surveyResultService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private SurveyResultController surveyResultController;


    @Test
    void getSurveyResult_shouldReturnObject() {
        when(surveyResultService.getSurveyResult(anyLong())).thenReturn(surveyResult);
        ResponseEntity<SurveyResultDTO> response = surveyResultController.getSurveyResult(surveyResult.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(surveyResultDTO));
    }

    @Test
    void deleteSurveyResult_shouldReturnTrue() {
        when(surveyResultService.deleteSurveyResult(anyLong())).thenReturn(true);

        ResponseEntity<Boolean> response = surveyResultController.deleteSurveyResult(surveyResult.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(true));
    }

    @Test
    void deleteSurveyResult_shouldReturnFalse() {
        ResponseEntity<Boolean> response = surveyResultController.deleteSurveyResult(surveyResult.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(false));
    }

    @Test
    void setSurveyResult_createResult() {
        when(surveyResultService.setSurveyResult(any(SurveyResult.class), anyLong())).thenReturn(surveyResult);
        when(modelMapper.surveyResultMapper(any(SurveyResultDTO.class))).thenReturn(surveyResult);

        ResponseEntity<SurveyResultDTO> response = surveyResultController.setSurveyResult(surveyResult.getId()
                , surveyResultDTO);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(surveyResultDTO));
    }

}
