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
import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.services.interfaces.QueryService;

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
public class QueryInitTests {

    private final String apiPath = "/api/que";
    private final Query query = new Query(1L, 1L, "test", false, 0L, 1L, new ArrayList<>());
    @Autowired
    private MockMvc mvc;
    @MockBean
    private QueryService queryService;

    @Test
    public void setQuery_expectContainQuestion() throws Exception {
        when(queryService.setQuery(any())).thenReturn(query);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(query);

        mvc.perform(post(apiPath).content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString(query.getQuestion())));
    }

    @Test
    public void getQuery_expectContainQuestion() throws Exception {
        when(queryService.getQuery(anyLong())).thenReturn(query);

        mvc.perform(get(apiPath + "/" + query.getId())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(query.getQuestion())));


    }

    @Test
    public void deleteQuery_expectContainTrue() throws Exception {
        when(queryService.deleteQuery(anyLong())).thenReturn(true);
        mvc.perform(delete(apiPath + "/" + query.getId())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }
}
