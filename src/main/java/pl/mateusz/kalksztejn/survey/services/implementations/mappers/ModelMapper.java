package pl.mateusz.kalksztejn.survey.services.implementations.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.*;
import pl.mateusz.kalksztejn.survey.models.dto.PersonalDataDTO;
import pl.mateusz.kalksztejn.survey.models.dto.QueryDTO;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyFilterDTO;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyResultDTO;
import pl.mateusz.kalksztejn.survey.repositorys.*;

import java.util.Optional;

@Service
public class ModelMapper {
    PaymentRepository paymentRepository;
    PersonalDataRepository personalDataRepository;
    QueryRepository queryRepository;
    SurveyFilterRepository surveyFilterRepository;
    SurveyRepository surveyRepository;
    SurveyResultRepository surveyResultRepository;
    UserRepository userRepository;

    @Autowired
    public ModelMapper(PaymentRepository paymentRepository, PersonalDataRepository personalDataRepository
            , QueryRepository queryRepository, SurveyFilterRepository surveyFilterRepository
            , SurveyRepository surveyRepository, SurveyResultRepository surveyResultRepository
            , UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.personalDataRepository = personalDataRepository;
        this.queryRepository = queryRepository;
        this.surveyFilterRepository = surveyFilterRepository;
        this.surveyRepository = surveyRepository;
        this.surveyResultRepository = surveyResultRepository;
        this.userRepository = userRepository;
    }

    public SurveyResult surveyResultMapper(SurveyResultDTO resultDTO) {
        SurveyResult result = new SurveyResult();
        result.setId(result.getId());
        result.setSurvey(surveyRepository.getById(resultDTO.getSurveyId()));
        result.setUser(userRepository.getById(resultDTO.getUserEmail()));
        result.setResponses(resultDTO.getResponses());

        return result;
    }

    public Query queryMapper(QueryDTO queryDTO) {
        Query query = new Query();
        query.setId(queryDTO.getId());
        query.setNumberOfQuery(queryDTO.getNumberOfQuery());
        query.setQuestion(queryDTO.getQuestion());
        query.setCheckQuery(queryDTO.isCheckQuery());
        query.setCorrectAnswer(queryDTO.getCorrectAnswer());
        query.setSurvey(surveyRepository.getById(queryDTO.getSurveyId()));
        query.setAnswers(queryDTO.getAnswers());

        return query;
    }
    public SurveyFilter surveyFilterMapper(SurveyFilterDTO surveyFilterDTO){
        SurveyFilter surveyFilter = new SurveyFilter();
        surveyFilter.setId(surveyFilterDTO.getId());
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyFilterDTO.getSurveyId());
        optionalSurvey.ifPresent(surveyFilter::setSurvey);
        surveyFilter.setAgeMin(surveyFilterDTO.getAgeMin());
        surveyFilter.setAgeMax(surveyFilterDTO.getAgeMax());
        surveyFilter.setGenders(surveyFilter.getGenders());
        surveyFilter.setSizeOfTheHometownMin(surveyFilterDTO.getSizeOfTheHometownMin());
        surveyFilter.setSizeOfTheHometownMax(surveyFilterDTO.getSizeOfTheHometownMax());
        surveyFilter.setSizeOfTownMin(surveyFilterDTO.getSizeOfTownMin());
        surveyFilter.setSizeOfTownMin(surveyFilterDTO.getSizeOfTownMax());
        surveyFilter.setGrossEarningsMin(surveyFilterDTO.getGrossEarningsMin());
        surveyFilter.setGrossEarningsMax(surveyFilterDTO.getGrossEarningsMax());
        surveyFilter.setEducations(surveyFilterDTO.getEducations());
        surveyFilter.setLaborSectors(surveyFilterDTO.getLaborSectors());
        surveyFilter.setMaritalStatuses(surveyFilterDTO.getMaritalStatuses());

        return surveyFilter;
    }
    public PersonalData personalDataMapper(PersonalDataDTO personalDataDTO){
        PersonalData personalData = new PersonalData();
        personalData.setId(personalDataDTO.getId());
        personalData.setGender(personalDataDTO.getGender());
        personalData.setSizeOfTheHometown(personalDataDTO.getSizeOfTheHometown());
        personalData.setSizeOfTown(personalDataDTO.getSizeOfTown());
        personalData.setGrossEarnings(personalDataDTO.getGrossEarnings());
        personalData.setEducation(personalDataDTO.getEducation());
        personalData.setLaborSector(personalDataDTO.getLaborSector());
        personalData.setMaritalStatus(personalDataDTO.getMaritalStatus());

        return personalData;
    }
}
