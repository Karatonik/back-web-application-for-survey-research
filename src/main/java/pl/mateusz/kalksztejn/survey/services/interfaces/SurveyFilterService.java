package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.SurveyFilter;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;

import java.util.List;

public interface SurveyFilterService {

    SurveyFilter set(SurveyFilter surveyFilter);

    SurveyFilter set(Long ageMin, Long ageMax, List<Gender> genders, Long sizeOfTheHometownMin
            , Long sizeOfTheHometownMax, Long sizeOfTownMin, Long sizeOfTownMax, Double grossEarningsMin
            , Double grossEarningsMax, List<Education> educations, List<LaborSector> laborSectors
            , List<MaritalStatus> maritalStatuses);

    boolean addSurveyToFilter(Long surveyId, Long surveyFilterId);

    boolean delete(Long surveyFilterId, String email);

}
