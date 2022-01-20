package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.PersonalData;
import pl.mateusz.kalksztejn.survey.models.SurveyFilter;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;
import pl.mateusz.kalksztejn.survey.models.payload.response.SurveyInfo;
import pl.mateusz.kalksztejn.survey.repositorys.PersonalDataRepository;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyFilterRepository;
import pl.mateusz.kalksztejn.survey.repositorys.UserRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.PersonalDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonalDataServiceImp implements PersonalDataService {

    PersonalDataRepository personalDataRepository;
    UserRepository userRepository;
    SurveyFilterRepository surveyFilterRepository;

    @Autowired
    public PersonalDataServiceImp(PersonalDataRepository personalDataRepository, UserRepository userRepository
            , SurveyFilterRepository surveyFilterRepository) {
        this.personalDataRepository = personalDataRepository;
        this.userRepository = userRepository;
        this.surveyFilterRepository = surveyFilterRepository;
    }

    @Override
    public List<User> findAllByParameters(SurveyFilter surveyFilter) {
        return personalDataRepository.findAllByParameters(surveyFilter.getAgeMin()
                , surveyFilter.getAgeMax(), surveyFilter.getGenders(), surveyFilter.getEducations()
                , surveyFilter.getSizeOfTheHometownMin(), surveyFilter.getSizeOfTheHometownMax()
                , surveyFilter.getSizeOfTownMin(), surveyFilter.getSizeOfTownMax()
                , surveyFilter.getGrossEarningsMin(), surveyFilter.getGrossEarningsMax()
                , surveyFilter.getLaborSectors(), surveyFilter.getMaritalStatuses());
    }

    @Override
    public List<User> findAllByParameters(Long ageMin, Long ageMax
            , List<Gender> genders, List<Education> educations
            , Long sizeOfTheHometownMin, Long sizeOfTheHometownMax
            , Long sizeOfTownMin, Long sizeOfTownMax, Double grossEarningsMin
            , Double grossEarningsMax, List<LaborSector> laborSectors
            , List<MaritalStatus> maritalStatuses) {
        return personalDataRepository.findAllByParameters(ageMin, ageMax, genders, educations, sizeOfTheHometownMin
                , sizeOfTheHometownMax, sizeOfTownMin, sizeOfTownMax, grossEarningsMin, grossEarningsMax, laborSectors
                , maritalStatuses);
    }

    @Override
    public PersonalData setPersonalData(PersonalData personalData) {
        personalData.setId(null);
        PersonalData optionalPersonalData = personalDataRepository.findByUser(personalData.getUser());
        if (optionalPersonalData == null) {
            Optional<User> optionalUser = userRepository.findById(personalData.getUser().getEmail());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                user.addPoints(500);
                userRepository.save(user);
            }

            return personalDataRepository.save(personalData);
        }
        return new PersonalData();
    }

    @Override
    @Modifying
    public PersonalData editPersonalData(PersonalData personalData) {
        Optional<User> optionalUser = userRepository.findById(personalData.getUser().getEmail());
        if (optionalUser.isPresent()) {
            return personalDataRepository.save(personalData);
        }
        return new PersonalData();
    }

    @Override
    public PersonalData setPersonalData(Long age, Gender gender, Long sizeOfTheHometown, Long sizeOfTown, Double grossEarnings
            , Education education, LaborSector laborSector, MaritalStatus maritalStatus, String email) {
        Optional<User> optionalUser = userRepository.findById(email);
        return optionalUser.map(user -> personalDataRepository
                .save(new PersonalData(age, gender, sizeOfTheHometown, sizeOfTown, grossEarnings
                        , education, laborSector, maritalStatus, user))).orElseGet(PersonalData::new);
    }

    @Override
    public PersonalData getPersonalData(Long personalDataId) {
        Optional<PersonalData> optionalPersonalData = personalDataRepository.findById(personalDataId);
        return optionalPersonalData.orElseGet(PersonalData::new);
    }


    @Override
    public PersonalData getPersonalDataByUser(String email) {
        Optional<User> optionalUser = userRepository.findById(email);
        if (optionalUser.isPresent()) {
            return personalDataRepository.findByUser(optionalUser.get());
        }
        return new PersonalData();
    }

    @Override
    public List<SurveyInfo> getSurveys(Long pId, String email) {
        Optional<PersonalData> optionalPersonalData = personalDataRepository.findById(pId);
        if (optionalPersonalData.isPresent()) {
            PersonalData personalData = optionalPersonalData.get();
            if (personalData.getUser().getEmail().equals(email)) {
                List<SurveyFilter> surveyFilters = surveyFilterRepository.findAll();
                return surveyFilters.stream().parallel()
                        .filter(f -> f.getSurvey().getOwner() != personalData.getUser())
                        .filter(f -> f.getSurvey().getResults().stream().parallel().noneMatch(r -> r.getUser() == personalData.getUser()))
                        .filter(f -> f.getAgeMin() <= personalData.getAge() && f.getAgeMax() >= personalData.getAge())
                        .filter(f -> f.getGrossEarningsMin() <= personalData.getGrossEarnings() && f.getGrossEarningsMax() >= personalData.getGrossEarnings())
                        .filter(f -> f.getSizeOfTheHometownMin() <= personalData.getSizeOfTheHometown() && f.getSizeOfTheHometownMax() >= personalData.getSizeOfTheHometown())
                        .filter(f -> f.getSizeOfTownMin() <= personalData.getSizeOfTown() && f.getSizeOfTownMax() >= personalData.getSizeOfTown())
                        .filter(f -> f.getGenders().contains(personalData.getGender()))
                        .filter(f -> f.getEducations().contains(personalData.getEducation()))
                        .filter(f -> f.getLaborSectors().contains(personalData.getLaborSector()))
                        .filter(f -> f.getMaritalStatuses().contains(personalData.getMaritalStatus()))
                        .map(s -> new SurveyInfo(s.getSurvey().getId(), s.getSurvey().getName())).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }
}
