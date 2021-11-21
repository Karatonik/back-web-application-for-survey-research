package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyResult;

import java.util.List;

public interface SurveyService {

    Survey get(Long surveyId, String email);

    Survey set(String name, String email);

    boolean delete(Long surveyId, String email);

    List<Survey> getAllByEmail(String email);

    List<SurveyResult> getSurveyResults(Long surveyId, String email);

    List<Query> getQueries(Long surveyId);

}
