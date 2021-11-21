package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.Query;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.SurveyResult;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.repositorys.SurveyRepository;
import pl.mateusz.kalksztejn.survey.repositorys.UserRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SurveyServiceImp implements SurveyService {

    SurveyRepository surveyRepository;
    UserRepository userRepository;

    @Autowired
    public SurveyServiceImp(SurveyRepository surveyRepository, UserRepository userRepository) {
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
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
                surveyRepository.delete(optionalSurvey.get());
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

    @Override
    public List<Query> getQueries(Long surveyId) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            return optionalSurvey.get().getQueries();
        }
        return new ArrayList<>();
    }

}
