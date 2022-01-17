package pl.mateusz.kalksztejn.survey.unitTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.mateusz.kalksztejn.survey.controllers.QueryController;
import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.services.interfaces.QueryService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QueryUnitTests {

    @Mock
    QueryService queryService;

    @InjectMocks
    QueryController queryController;

    Query query = new Query();
    @Test
    void setQuery_CreateQuery() {
        query.setId(1L);

        when(queryService.setQuery(any(Query.class))).thenReturn(query);

        ResponseEntity<Query> response = queryController.setQuery(query);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(query));

    }

    @Test
    void getQuery_shouldReturnQuery() {
        query.setId(1L);

        when(queryService.getQuery(anyLong())).thenReturn(query);
        ResponseEntity<Query> response = queryController.getQuery(1L);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(query));
    }

    @Test
    void deleteQuery_shouldReturnFalse() {
        ResponseEntity<Boolean> response = queryController.deleteQuery(1L);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(false));
    }

    @Test
    void deleteQuery_shouldReturnTrue() {
        query.setId(1L);

        when(queryService.deleteQuery(anyLong())).thenReturn(true);
        ResponseEntity<Boolean> response = queryController.deleteQuery(1L);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is(true));
    }
}
