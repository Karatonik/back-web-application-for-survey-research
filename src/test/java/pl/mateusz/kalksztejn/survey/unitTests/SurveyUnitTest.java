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
import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.dto.QueryDTO;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.MailService;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
public class SurveyUnitTest {
    private final String apiPath = "/api/survey";
    private final User user = new User("test@mail.com", "password123456789");
    private final Survey survey = new Survey(1L, "Test survey", user, new ArrayList<>(), new ArrayList<>());
    private final Query query = new Query(1L, 1L, "test", false, 0L, 1L, new ArrayList<>());
    @Autowired
    private MockMvc mvc;
    @MockBean
    private SurveyService surveyService;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private MailService mailService;

    @Test
    public void getTest() throws Exception {
        when(surveyService.getSurvey(anyLong(), anyString())).thenReturn(survey);

        mvc.perform(get(apiPath + "/" + survey.getId() + "/" + user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Test survey")));
    }

    @Test
    public void setTest() throws Exception {
        when(surveyService.setSurvey(anyString(), anyString())).thenReturn(survey);


        mvc.perform(post(apiPath + "/" + survey.getName() + "/" + user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Test survey")));

    }

    @Test
    public void deleteTest() throws Exception {
        when(surveyService.deleteSurvey(anyLong(), anyString())).thenReturn(true);

        mvc.perform(delete(apiPath + "/" + survey.getId() + "/" + user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));

    }

    @Test
    public void getResultsTest() throws Exception {
        when(surveyService.getSurveyResults(anyLong(), anyString())).thenReturn(new ArrayList<>());

        mvc.perform(get(apiPath + "/res/" + survey.getId() + "/" + user.getEmail())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getQueriesTest() throws Exception {
        when(surveyService.getQueries(anyLong())).thenReturn(new ArrayList<>());

        mvc.perform(get(apiPath + "/que/" + survey.getId())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void getAllByEmailTest() throws Exception {
        when(surveyService.getAllByEmail(anyString())).thenReturn(new ArrayList<>());
        mvc.perform(get(apiPath + "/" + user.getEmail())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void setQueriesTest() throws Exception {

        List<Query> queries = new ArrayList<>();
        queries.add(query);
        QueryDTO queryDTO = new QueryDTO(query);
        List<QueryDTO> queryDTOList = queries.stream().map(QueryDTO::new).collect(Collectors.toList());

        when(surveyService.setQueries(anyString(), anyLong(), anyList())).thenReturn(queries);
        when(modelMapper.queryMapper(any())).thenReturn(query);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(queryDTOList);

        mvc.perform(put(apiPath + "/que/" + survey.getId() + "/" + user.getEmail()).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void inviteRespondentsTest() throws Exception {
        when(surveyService.getRespondentsList(anyString(), anyLong())).thenReturn(new ArrayList<>());
        when(mailService.sendMailsWithSurvey(anyList(), anyLong())).thenReturn(true);

        mvc.perform(get(apiPath + "/inv/" + survey.getId() + "/" + user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    public void getRespQueriesTest() throws Exception {
        when(surveyService.getRespQueries(anyLong(), anyLong(), anyString())).thenReturn(new ArrayList<>());
        mvc.perform(get(apiPath + "/resp/1/" + user.getEmail() + "/" + survey.getId())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk());
    }


}
