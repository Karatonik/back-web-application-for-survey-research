package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.SurveyResult;

public interface SurveyResultService {

    SurveyResult getSurveyResult(Long Id);

    boolean deleteSurveyResult(Long Id);

    SurveyResult setSurveyResult(SurveyResult surveyResult, Long surveyId);
}
