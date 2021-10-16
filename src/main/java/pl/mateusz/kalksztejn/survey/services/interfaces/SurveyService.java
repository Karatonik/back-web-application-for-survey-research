package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyResult;
import pl.mateusz.kalksztejn.survey.models.User;

import java.util.List;

public interface SurveyService {

    Survey get(Long surveyId);

    User getOwner(Long surveyId);

    List<SurveyResult> getSurveyResults(Long surveyId);

    List<Query> getQueries(Long surveyId);
}
