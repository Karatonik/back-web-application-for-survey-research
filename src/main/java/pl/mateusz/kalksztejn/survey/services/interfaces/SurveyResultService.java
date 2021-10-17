package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.SurveyResult;

public interface SurveyResultService {

    SurveyResult get(Long Id);

    SurveyResult getByUser(String email ,Long surveyId);

    boolean delete(Long Id);

    SurveyResult set(SurveyResult surveyResult ,Long surveyId);
}
