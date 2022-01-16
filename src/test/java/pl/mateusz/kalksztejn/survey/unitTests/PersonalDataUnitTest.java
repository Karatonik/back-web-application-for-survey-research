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
import pl.mateusz.kalksztejn.survey.models.PersonalData;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.dto.PersonalDataDTO;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.PersonalDataService;

import java.util.ArrayList;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = {SurveyApplication.class})
@SpringBootTest
public class PersonalDataUnitTest {
    private final String apiPath = "/api/pd";
    @Autowired
    private MockMvc mvc;
    @MockBean
    private PersonalDataService personalDataService;

    @MockBean
    private ModelMapper modelMapper;

    private final User user = new User("test@mail.com","test123456");

    private final PersonalData personalData = new PersonalData(1L,18L, Gender.male,100000L,
            100000L,3000.0, Education.engineer, LaborSector.unemployment,
            MaritalStatus.single,user);
    private final PersonalDataDTO personalDataDTO= new PersonalDataDTO(personalData);


    @Test
    public void setTest() throws Exception{
        when(personalDataService.setPersonalData(personalData)).thenReturn(personalData);
        when(modelMapper.personalDataMapper(any())).thenReturn(personalData);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(personalDataDTO );

        mvc.perform(post(apiPath).content(requestJson)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string(containsString("test@mail.com")));
    }
    @Test
    public void getTest() throws Exception{
        when(personalDataService.getPersonalData(anyLong())).thenReturn(personalData);

        mvc.perform(get(apiPath+"/"+personalData.getId())
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test@mail.com")));
    }
    @Test
    public void getByUserTest() throws Exception{
        when(personalDataService.getPersonalDataByUser(anyString())).thenReturn(personalData);

        mvc.perform(get(apiPath+"/e/"+user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("test@mail.com")));
    }
    @Test
    public void getSurveysTest() throws Exception{
        when(personalDataService.getSurveys(anyLong(),anyString())).thenReturn(new ArrayList<>());

        mvc.perform(get(apiPath+"/s/"+personalData.getId()+"/"+user.getEmail())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL))
                .andDo(print()).andExpect(status().isOk());
    }
}
