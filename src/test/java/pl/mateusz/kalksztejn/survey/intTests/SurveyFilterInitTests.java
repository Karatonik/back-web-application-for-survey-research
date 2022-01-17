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
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyFilter;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyFilterDTO;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyFilterService;

import java.util.ArrayList;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SurveyApplication.class})
@SpringBootTest
public class SurveyFilterInitTests {
    private final String apiPath = "/api/fil";
    private final User user = new User("test@mail.com", "password123456789");
    private final Survey survey = new Survey(1L, "Test survey", user, new ArrayList<>(), new ArrayList<>());
    private final SurveyFilter surveyFilter = new SurveyFilter(1L, survey, 18L, 99L, new ArrayList<>(),
            100000L, 1000000L, 10000L, 1000000L,
            0.0, 90000.0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    private final SurveyFilterDTO surveyFilterDTO = new SurveyFilterDTO(surveyFilter);
    @Autowired
    private MockMvc mvc;
    @MockBean
    private SurveyFilterService surveyFilterService;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void setSurveyFilter_expectStatusOk() throws Exception {
        when(surveyFilterService.setSurveyFilter(any())).thenReturn(surveyFilter);
        when(modelMapper.surveyFilterMapper(surveyFilterDTO)).thenReturn(surveyFilter);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(surveyFilterDTO);


        mvc.perform(post(apiPath).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk());

    }

    @Test
    public void editSurveyFilter_expectStatusOk() throws Exception {
        when(surveyFilterService.editSurveyFilter(any())).thenReturn(surveyFilter);
        when(modelMapper.surveyFilterMapper(any())).thenReturn(surveyFilter);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(surveyFilterDTO);

        mvc.perform(put(apiPath).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void addSurveyToFilter_expectStatusOk() throws Exception {
        when(surveyFilterService.addSurveyToFilter(anyLong(), anyLong())).thenReturn(true);

        mvc.perform(put(apiPath + "/" + survey.getId() + "/" + surveyFilter.getId())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void deleteSurveyFilter_expectContainTrue() throws Exception {
        when(surveyFilterService.deleteSurveyFilter(anyLong(), anyString())).thenReturn(true);

        mvc.perform(delete(apiPath + "/" + surveyFilter.getId() + "/" + user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void getBySurveyId_expectStatusOk() throws Exception {
        when(surveyFilterService.getBySurveyId(anyLong(), anyString())).thenReturn(surveyFilter);

        mvc.perform(get(apiPath + "/" + surveyFilter.getId() + "/" + user.getEmail())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk());


    }
}
