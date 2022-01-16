package pl.mateusz.kalksztejn.survey.unitTests;

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
import pl.mateusz.kalksztejn.survey.models.SurveyResult;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyResultDTO;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyResultService;

import java.util.ArrayList;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SurveyApplication.class})
@SpringBootTest
public class SurveyResultUnitTest {
    private final String apiPath = "/api/res";
    private final User user = new User("test@mail.com", "password123456789");
    private final Survey survey = new Survey(1L, "Test survey", user, new ArrayList<>(), new ArrayList<>());
    private final SurveyResult surveyResult = new SurveyResult(1L, user, new ArrayList<>());
    private final SurveyResultDTO surveyResultDTO = new SurveyResultDTO(surveyResult);
    @Autowired
    private MockMvc mvc;
    @MockBean
    private SurveyResultService surveyResultService;
    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void getTest() throws Exception {

        when(surveyResultService.getSurveyResult(anyLong())).thenReturn(surveyResult);


        mvc.perform(get(apiPath + "/" + surveyResult.getId())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test@mail.com")));
    }

    @Test
    public void deleteTest() throws Exception {
        when(surveyResultService.deleteSurveyResult(anyLong())).thenReturn(true);

        mvc.perform(delete(apiPath + "/" + surveyResult.getId())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void setTest() throws Exception {
        when(surveyResultService.setSurveyResult(any(), anyLong())).thenReturn(surveyResult);
        when(modelMapper.surveyResultMapper(any())).thenReturn(surveyResult);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(surveyResultDTO);

        mvc.perform(post(apiPath + "/" + surveyResult.getId()).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test@mail.com")));
    }
}
