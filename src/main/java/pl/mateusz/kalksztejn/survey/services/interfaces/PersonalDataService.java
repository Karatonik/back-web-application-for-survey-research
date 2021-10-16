package pl.mateusz.kalksztejn.survey.services.interfaces;

import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;

import java.util.List;

public interface PersonalDataService {

    //todo wszystkie parametry które znajdą kandydatów
    List<User> findAllByParameters(Long ageMin, Long ageMax, List<Gender> genders
            ,List<Education> educations,Long sizeOfTheHometownMin
            ,Long sizeOfTheHometownMax,Long sizeOfTownMin ,Long sizeOfTownMax
            ,Long grossEarningsMin ,Long grossEarningsMax
            ,List<LaborSector> laborSectors ,List<MaritalStatus> maritalStatuses);
}
