package pl.mateusz.kalksztejn.survey.integrationTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mateusz.kalksztejn.survey.SurveyApplication;
import pl.mateusz.kalksztejn.survey.models.PersonalData;
import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.repositorys.QueryRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.QueryService;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SurveyApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrations.properties")
public class QueryIntTest {

    @Autowired
    private QueryService queryService;

    Query query = new Query(1L,1L,"TEST",false,0L,1L,new ArrayList<>());
    public void setTest(){
        assertEquals(queryService.set(query).getQuestion(),query.getQuestion());
    }
    @Test
    public void getTest(){
       query= queryService.set(query);
       Query query1 =queryService.get(query.getId());
        assertEquals(query1.getId(),query.getId());
    }
    @Test
    public void delete(){
        query= queryService.set(query);
        assertTrue(queryService.delete(query.getId()));
    }


}
