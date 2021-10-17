package pl.mateusz.kalksztejn.survey.services.implementations.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.SurveyResult;
import pl.mateusz.kalksztejn.survey.models.dto.QueryDTO;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyResultDTO;
import pl.mateusz.kalksztejn.survey.repositorys.*;

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
}
