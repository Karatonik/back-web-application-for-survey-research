package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.SurveyResult;

public interface SurveyResultService {

    SurveyResult get(Long id);

    SurveyResult getByUser(String email ,Long surveyId);

    SurveyResult set(SurveyResult surveyResult ,Long surveyId);
}
