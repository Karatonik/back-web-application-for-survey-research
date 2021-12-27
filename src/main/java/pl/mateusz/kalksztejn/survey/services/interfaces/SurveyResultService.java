package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.SurveyResult;

public interface SurveyResultService {

    SurveyResult get(Long Id);

    boolean delete(Long Id);

    SurveyResult set(SurveyResult surveyResult, Long surveyId);
}
