package pl.mateusz.kalksztejn.survey.integrationTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mateusz.kalksztejn.survey.SurveyApplication;
import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.services.interfaces.QueryService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SurveyApplication.class)
@TestPropertySource(
        locations = "classpath:application-integrations.properties")
public class QueryIntTest {

    Query query = new Query(1L, 1L, "TEST", false, 0L, 1L, new ArrayList<>());
    @Autowired
    private QueryService queryService;

    @Test
    public void setTest() {
        assertEquals(queryService.setQuery(query).getQuestion(), query.getQuestion());
    }

    @Test
    public void getTest() {
        query = queryService.setQuery(query);
        Query query1 = queryService.getQuery(query.getId());
        assertEquals(query1.getId(), query.getId());
    }

    @Test
    public void deleteTest() {
        query = queryService.setQuery(query);
        assertTrue(queryService.deleteQuery(query.getId()));
    }


}
