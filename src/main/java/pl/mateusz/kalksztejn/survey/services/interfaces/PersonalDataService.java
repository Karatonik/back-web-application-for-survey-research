package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.PersonalData;
import pl.mateusz.kalksztejn.survey.models.SurveyFilter;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;
import pl.mateusz.kalksztejn.survey.repositorys.PersonalDataRepository;

import java.util.List;

public interface PersonalDataService {

    List<User> findAllByParameters(SurveyFilter surveyFilter);

    List<User> findAllByParameters(Long ageMin, Long ageMax, List<Gender> genders
            ,List<Education> educations,Long sizeOfTheHometownMin
            ,Long sizeOfTheHometownMax,Long sizeOfTownMin ,Long sizeOfTownMax
            ,Double grossEarningsMin ,Double grossEarningsMax
            ,List<LaborSector> laborSectors ,List<MaritalStatus> maritalStatuses);

    PersonalData set(PersonalData personalData);

    PersonalData set(Long age, Gender gender, Long sizeOfTheHometown, Long sizeOfTown, Double grossEarnings
            , Education education, LaborSector laborSector, MaritalStatus maritalStatus, String email);

    PersonalData get(Long personalDataId);

    PersonalData getByUser(String email);
}
