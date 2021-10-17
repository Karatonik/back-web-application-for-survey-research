package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.repositorys.QueryRepository;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.QueryService;

import java.util.*;

@Service
public class QueryServiceImp implements QueryService {

    QueryRepository queryRepository;
    SurveyRepository surveyRepository;

    @Autowired
    public QueryServiceImp(QueryRepository queryRepository, SurveyRepository surveyRepository) {
        this.queryRepository = queryRepository;
        this.surveyRepository = surveyRepository;
    }

    @Override
    public Query set(Long surveyId,Long numberOfQuery, String question, ArrayList<String>  answers
            ,boolean checkQuery ,Long correctAnswer) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if(optionalSurvey.isPresent()){

            Survey survey = optionalSurvey.get();
            //todo dodaj do survey jeżeli nie pojawi się w jej liście
            //List<Query> queryList = survey.getQueries();

            Query query = new Query(survey,numberOfQuery,question,answers,checkQuery,correctAnswer);
            queryRepository.save(query);
        }
        return queryRepository.save(new Query());
    }

    @Override
    public Query get(Long queryId) {
        return queryRepository.getById(queryId);
    }

    @Override
    public boolean delete(Long queryId) {
        Optional<Query> optionalQuery = queryRepository.findById(queryId);

        if(optionalQuery.isPresent()){
            queryRepository.delete(optionalQuery.get());
            return true;
        }
        return false;
    }

}
