package pl.mateusz.kalksztejn.survey.services.interfaces;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import pl.mateusz.kalksztejn.survey.models.*;
import pl.mateusz.kalksztejn.survey.models.dto.ResultDTO;

import java.io.IOException;
import java.util.List;

public interface SurveyService {

    Survey getSurvey(Long Id, String email);

    Survey setSurvey(String name, String email);

    boolean deleteSurvey(Long Id, String email);

    List<Survey> getAllByEmail(String email);

    List<List<ResultDTO>> getSurveyResults(Long Id, String email);

    List<Query> setQueries(String email , Long Id, List<Query> queryList);

    List<Query> getQueries(Long surveyId);

    List<User> getRespondentsList(String email, Long Id);

    List<Query> getRespQueries (Long pId, Long Id, String email);

    ResponseEntity<Resource> getCSV (Long Id, String email) throws IOException;

}
