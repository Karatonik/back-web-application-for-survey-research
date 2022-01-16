package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.repositorys.QueryRepository;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.QueryService;

import java.util.ArrayList;
import java.util.Optional;

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
    public Query setQuery(Query query) {
        return queryRepository.save(query);
    }


    @Override
    public Query getQuery(Long queryId) {
        return queryRepository.getById(queryId);
    }

    @Override
    public boolean deleteQuery(Long queryId) {
        Optional<Query> optionalQuery = queryRepository.findById(queryId);

        if (optionalQuery.isPresent()) {
            queryRepository.delete(optionalQuery.get());
            return true;
        }
        return false;
    }

}
