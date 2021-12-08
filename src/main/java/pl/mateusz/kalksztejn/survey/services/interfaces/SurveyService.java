package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyResult;
import pl.mateusz.kalksztejn.survey.models.User;

import java.util.List;

public interface SurveyService {

    Survey get(Long surveyId, String email);

    Survey set(String name, String email);

    boolean delete(Long surveyId, String email);

    List<Survey> getAllByEmail(String email);

    List<SurveyResult> getSurveyResults(Long surveyId, String email);

    List<Query> setQueries(String email , Long surveyId ,List<Query> queryList);

    List<Query> getQueries(Long surveyId);

    List<User> getRespondentsList(String email, Long surveyId);

    List<Query> getRespQueries (Long pId,Long sId,String email);

}
