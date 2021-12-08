package pl.mateusz.kalksztejn.survey.services.implementations;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.*;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyDTO;
import pl.mateusz.kalksztejn.survey.repositorys.*;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyServiceImp implements SurveyService {

    SurveyRepository surveyRepository;
    UserRepository userRepository;
    QueryRepository queryRepository;
    SurveyFilterRepository surveyFilterRepository;
    PersonalDataRepository personalDataRepository;
    SurveyResultRepository surveyResultRepository;

    @Autowired
    public SurveyServiceImp(SurveyRepository surveyRepository, UserRepository userRepository, QueryRepository queryRepository,
                            SurveyFilterRepository surveyFilterRepository,PersonalDataRepository personalDataRepository
                            ,SurveyResultRepository surveyResultRepository) {
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
        this.queryRepository = queryRepository;
        this.surveyFilterRepository=surveyFilterRepository;
        this.personalDataRepository = personalDataRepository;
        this.surveyResultRepository = surveyResultRepository;

    }







    @Override
    public Survey get(Long surveyId, String email) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            if (survey.getOwner().getEmail().equals(email)) {
                return survey;
            }
        }
        return new Survey();
    }

    @Override
    public Survey set(String name, String email) {
        System.out.println(email);
        Optional<User> optionalUser = userRepository.findById(email);


        return optionalUser.map(user -> surveyRepository.save(new Survey(name, user))).orElseGet(Survey::new);
    }

    @Override
    public boolean delete(Long surveyId, String email) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            if (survey.getOwner().getEmail().equals(email)) {

                Optional<SurveyFilter>optionalSurveyFilter = surveyFilterRepository.findBySurvey(survey);
                optionalSurveyFilter.ifPresent(surveyFilter -> surveyFilterRepository.delete(surveyFilter));

                surveyResultRepository.deleteAll(survey.getResults());

                queryRepository.deleteAll(survey.getQueries());

                surveyRepository.delete(survey);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Survey> getAllByEmail(String email) {
        Optional<User> optionalUser = userRepository.findById(email);
        List<Survey> surveys = new ArrayList<>();
        return optionalUser.map(user -> surveyRepository.findAllByOwner(user)).orElse(surveys);
    }

    @Override
    public List<SurveyResult> getSurveyResults(Long surveyId, String email) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            if (survey.getOwner().getEmail().equals(email)) {
                return optionalSurvey.get().getResults();
            }
        }
        return new ArrayList<>();
    }
    @Modifying
    @Override
    public List<Query> setQueries(String email, Long surveyId, List<Query> queryList) {

        List<Query> queriesEmpty = new ArrayList<>();
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();

            if (survey.getOwner().getEmail().equals(email)) {
                queryList = queryRepository.saveAll(queryList);
                survey.setQueries(queryList);
                survey = surveyRepository.save(survey);
                return survey.getQueries();
            }

        }
        return queriesEmpty;
    }

    @Override
    public List<Query> getQueries(Long surveyId) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            return optionalSurvey.get().getQueries();
        }
        return new ArrayList<>();
    }

    @Override
    public List<User> getRespondentsList(String email, Long surveyId) {
        Optional<Survey> optionalSurvey =surveyRepository.findById(surveyId);
        if(optionalSurvey.isPresent()){
            Survey survey = optionalSurvey.get();
            if(survey.getOwner().getEmail().equals(email)){
            Optional<SurveyFilter> optionalSurveyFilter= surveyFilterRepository.findBySurvey(survey);
            if(optionalSurveyFilter.isPresent()){
                SurveyFilter surveyFilter = optionalSurveyFilter.get();
             return   personalDataRepository.findAllByParameters(surveyFilter.getAgeMin(),surveyFilter.getAgeMax()
                        ,surveyFilter.getGenders(),surveyFilter.getEducations(),surveyFilter.getSizeOfTheHometownMin()
                        ,surveyFilter.getSizeOfTheHometownMax(),surveyFilter.getSizeOfTownMin(),surveyFilter.getSizeOfTownMax()
                        ,surveyFilter.getGrossEarningsMin(),surveyFilter.getGrossEarningsMax(),surveyFilter.getLaborSectors()
                        ,surveyFilter.getMaritalStatuses());
            }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<Query> getRespQueries(Long pId, Long sId, String email) {
        Optional<PersonalData> optionalPersonalData = personalDataRepository.findById(pId);
        if(optionalPersonalData.isPresent()){
            PersonalData personalData= optionalPersonalData.get();
            Optional<Survey> optionalSurvey = surveyRepository.findById(sId);
            if(optionalSurvey.isPresent() &&personalData.getUser().getEmail().equals(email) ){
                Survey survey = optionalSurvey.get();
               if(survey.getResults().stream().noneMatch(r ->r.getUser() == personalData.getUser())){
                   return survey.getQueries();
               }
            }
        }
        return new ArrayList<>();
    }
}
