package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.PersonalData;
import pl.mateusz.kalksztejn.survey.models.SurveyFilter;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;
import pl.mateusz.kalksztejn.survey.repositorys.PersonalDataRepository;
import pl.mateusz.kalksztejn.survey.repositorys.UserRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.PersonalDataService;

import java.util.List;
import java.util.Optional;

@Service
public class PersonalDataServiceImp implements PersonalDataService {

    PersonalDataRepository personalDataRepository;
    UserRepository userRepository;

    @Autowired
    public PersonalDataServiceImp(PersonalDataRepository personalDataRepository, UserRepository userRepository) {
        this.personalDataRepository = personalDataRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllByParameters(SurveyFilter surveyFilter) {
        return personalDataRepository.findAllByParameters(surveyFilter.getAgeMin()
                ,surveyFilter.getAgeMax(),surveyFilter.getGenders(), surveyFilter.getEducations()
                ,surveyFilter.getSizeOfTheHometownMin(), surveyFilter.getSizeOfTheHometownMax()
                ,surveyFilter.getSizeOfTownMin(),surveyFilter.getSizeOfTownMax()
                ,surveyFilter.getGrossEarningsMin(),surveyFilter.getGrossEarningsMax()
                ,surveyFilter.getLaborSectors(),surveyFilter.getMaritalStatuses());
    }

    @Override
    public List<User> findAllByParameters(Long ageMin, Long ageMax
            , List<Gender> genders, List<Education> educations
            , Long sizeOfTheHometownMin, Long sizeOfTheHometownMax
            , Long sizeOfTownMin, Long sizeOfTownMax, Double grossEarningsMin
            , Double grossEarningsMax, List<LaborSector> laborSectors
            , List<MaritalStatus> maritalStatuses) {
        return personalDataRepository.findAllByParameters(ageMin,ageMax,genders,educations,sizeOfTheHometownMin
                ,sizeOfTheHometownMax,sizeOfTownMin,sizeOfTownMax,grossEarningsMin,grossEarningsMax,laborSectors
                ,maritalStatuses);
    }

    @Override
    public PersonalData set(PersonalData personalData) {
        return null;
    }

    @Override
    public PersonalData set(Long age, Gender gender, Long sizeOfTheHometown, Long sizeOfTown, Double grossEarnings
            , Education education, LaborSector laborSector, MaritalStatus maritalStatus, String email) {
        Optional<User> optionalUser = userRepository.findById(email);
        return optionalUser.map(user -> personalDataRepository
                .save(new PersonalData(age, gender, sizeOfTheHometown, sizeOfTown, grossEarnings
                , education, laborSector, maritalStatus, user))).orElseGet(PersonalData::new);
    }

    @Override
    public PersonalData get(Long personalDataId) {
        Optional<PersonalData> optionalPersonalData = personalDataRepository.findById(personalDataId);
        return optionalPersonalData.orElseGet(PersonalData::new);
    }

    @Override
    public PersonalData getByUser(String email) {
        Optional<User> optionalUser = userRepository.findById(email);
        if(optionalUser.isPresent()){
            return personalDataRepository.findByUser(optionalUser.get());
        }
        return new PersonalData();
    }
}
