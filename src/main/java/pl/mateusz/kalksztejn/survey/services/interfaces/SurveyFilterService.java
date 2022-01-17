package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.SurveyFilter;

public interface SurveyFilterService {

    SurveyFilter setSurveyFilter(SurveyFilter surveyFilter);

    boolean addSurveyToFilter(Long surveyId, Long Id);

    SurveyFilter editSurveyFilter(SurveyFilter surveyFilter);

    boolean deleteSurveyFilter(Long Id, String email);

    SurveyFilter getBySurveyId(Long surveyId, String email);

}
