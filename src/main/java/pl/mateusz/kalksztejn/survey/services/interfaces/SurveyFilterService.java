package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.SurveyFilter;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;

import java.util.List;

public interface SurveyFilterService {

    SurveyFilter set(SurveyFilter surveyFilter);

    boolean addSurveyToFilter(Long surveyId, Long Id);

    SurveyFilter edit(SurveyFilter surveyFilter);

    boolean delete(Long Id, String email);

    SurveyFilter getBySurveyId(Long surveyId, String email);

}
