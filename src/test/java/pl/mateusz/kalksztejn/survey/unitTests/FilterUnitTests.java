package pl.mateusz.kalksztejn.survey.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.mateusz.kalksztejn.survey.controllers.SurveyFilterController;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyFilter;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyFilterDTO;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyFilterService;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FilterUnitTests {


    private final User user = new User("test@mail.com", "password123456789");
    private final Survey survey = new Survey(1L, "Test survey", user, new ArrayList<>(), new ArrayList<>());
    private final SurveyFilter surveyFilter = new SurveyFilter(1L, survey, 18L, 99L, new ArrayList<>(),
            100000L, 1000000L, 10000L, 1000000L,
            0.0, 90000.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    private final SurveyFilterDTO surveyFilterDTO = new SurveyFilterDTO(surveyFilter);
    @Mock
    SurveyFilterService surveyFilterService;
    @Mock
    ModelMapper modelMapper;
    @InjectMocks
    SurveyFilterController surveyFilterController;

    @Test
    void setSurveyFilter_createFilter() {
        when(surveyFilterService.setSurveyFilter(any(SurveyFilter.class))).thenReturn(surveyFilter);
        when(modelMapper.surveyFilterMapper(any(SurveyFilterDTO.class))).thenReturn(surveyFilter);

        ResponseEntity<SurveyFilterDTO> response = surveyFilterController
                .setSurveyFilter(surveyFilterDTO);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(surveyFilterDTO));
    }

    @Test
    void editSurveyFilter_editFilter() {
        when(surveyFilterService.editSurveyFilter(any(SurveyFilter.class))).thenReturn(surveyFilter);
        when(modelMapper.surveyFilterMapper(any(SurveyFilterDTO.class))).thenReturn(surveyFilter);

        ResponseEntity<SurveyFilterDTO> response = surveyFilterController
                .editSurveyFilter(surveyFilterDTO);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(surveyFilterDTO));
    }

    @Test
    void addSurveyToFilter_shouldReturnFalse() {
        ResponseEntity<Boolean> response = surveyFilterController
                .addSurveyToFilter(survey.getId(), surveyFilter.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(false));
    }

    @Test
    void addSurveyToFilter_shouldReturnTrue() {
        when(surveyFilterService.addSurveyToFilter(anyLong(), anyLong())).thenReturn(true);

        ResponseEntity<Boolean> response = surveyFilterController
                .addSurveyToFilter(survey.getId(), surveyFilter.getId());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(true));
    }

    @Test
    void deleteSurveyFilter_shouldReturnFalse() {
        ResponseEntity<Boolean> response = surveyFilterController
                .deleteSurveyFilter(surveyFilter.getId(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(false));
    }

    @Test
    void deleteSurveyFilter_shouldReturnTrue() {
        when(surveyFilterService.deleteSurveyFilter(anyLong(), anyString())).thenReturn(true);

        ResponseEntity<Boolean> response = surveyFilterController
                .deleteSurveyFilter(surveyFilter.getId(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(true));
    }

    @Test
    void getBySurveyId_shouldReturnObject() {
        when(surveyFilterService.getBySurveyId(anyLong(), anyString())).thenReturn(surveyFilter);

        ResponseEntity<SurveyFilterDTO> response = surveyFilterController
                .getBySurveyId(surveyFilter.getId(), user.getEmail());

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(surveyFilterDTO));
    }

}
