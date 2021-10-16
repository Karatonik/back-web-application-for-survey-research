package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;
import pl.mateusz.kalksztejn.survey.repositorys.PersonalDataRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.PersonalDataService;

import java.util.List;

@Service
public class PersonalDataServiceImp implements PersonalDataService {

    private PersonalDataRepository personalDataRepository;


    public PersonalDataServiceImp(PersonalDataRepository personalDataRepository) {
        this.personalDataRepository = personalDataRepository;
    }

    @Override
    public List<User> findAllByParameters(Long ageMin, Long ageMax
            , List<Gender> genders, List<Education> educations
            , Long sizeOfTheHometownMin, Long sizeOfTheHometownMax
            , Long sizeOfTownMin, Long sizeOfTownMax, Long grossEarningsMin
            , Long grossEarningsMax, List<LaborSector> laborSectors
            , List<MaritalStatus> maritalStatuses) {
        return personalDataRepository.findAllByParameters(ageMin,ageMax,genders,educations,sizeOfTheHometownMin
                ,sizeOfTheHometownMax,sizeOfTownMin,sizeOfTownMax,grossEarningsMin,grossEarningsMax,laborSectors
                ,maritalStatuses);
    }
}
