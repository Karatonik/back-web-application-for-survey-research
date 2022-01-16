package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.models.*;
import pl.mateusz.kalksztejn.survey.models.dto.ResultDTO;
import pl.mateusz.kalksztejn.survey.repositorys.*;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyService;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                            SurveyFilterRepository surveyFilterRepository, PersonalDataRepository personalDataRepository
            , SurveyResultRepository surveyResultRepository) {
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
        this.queryRepository = queryRepository;
        this.surveyFilterRepository = surveyFilterRepository;
        this.personalDataRepository = personalDataRepository;
        this.surveyResultRepository = surveyResultRepository;

    }


    @Override
    public Survey getSurvey(Long surveyId, String email) {
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
    public Survey setSurvey(String name, String email) {
        System.out.println(email);
        Optional<User> optionalUser = userRepository.findById(email);


        return optionalUser.map(user -> surveyRepository.save(new Survey(name, user))).orElseGet(Survey::new);
    }

    @Override
    public boolean deleteSurvey(Long surveyId, String email) {
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            if (survey.getOwner().getEmail().equals(email)) {

                Optional<SurveyFilter> optionalSurveyFilter = surveyFilterRepository.findBySurvey(survey);
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


    public List<SurveyResult> getResultsFromDB(Long surveyId, String email){
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            if (survey.getOwner().getEmail().equals(email)) {
                List<Query> queries = optionalSurvey.get().getQueries();
                List<SurveyResult> surveyResults = optionalSurvey.get().getResults();
                return surveyResults.stream().filter(v -> {
                    boolean tempBoolean = true;
                    for (Query query : queries) {
                        if (query.isCheckQuery()) {
                            String correctValue = String.valueOf((char) (query.getCorrectAnswer().intValue() + 65));
                            String value = v.getResponses().get(query.getNumberOfQuery().intValue());
                            if (!value.contains(correctValue)) {
                                tempBoolean = false;
                            }
                        }
                    }
                    return tempBoolean;
                }).collect(Collectors.toList());
            }}
        return  new ArrayList<>();
    }


    @Override
    public List<List<ResultDTO>> getSurveyResults(Long surveyId, String email) {
                //getSurveyResults from DB
                List<SurveyResult>surveyResults = getResultsFromDB(surveyId,email);

                //map to List of Result List
                List<List<ResultDTO>> foundResultsList = surveyResults.stream().map(x -> x.getResponses().stream()
                        .map(z -> new ResultDTO(1, z))
                        .collect(Collectors.toList())).collect(Collectors.toList());
                //rearrange list and remove duplications
                List<List<ResultDTO>> resultsList = new ArrayList<>();
                if(foundResultsList.size()>0){

                for (int i = 0; i < foundResultsList.get(0).size(); i++) {
                    List<ResultDTO> resultList = new ArrayList<>();
                    for (List<ResultDTO> results : foundResultsList) {

                        ResultDTO result = results.get(i);

                        ResultDTO finalResult = result;
                        Optional<ResultDTO> optionalResult = resultList.stream()
                                .filter(x -> x.getName().equals(finalResult.getName())).findFirst();

                        if (optionalResult.isPresent()) {
                            resultList.remove(optionalResult.get());
                            result = optionalResult.get();
                            result.setValue(result.getValue() + 1);
                        }
                        resultList.add(result);
                    }
                    resultsList.add(resultList);
                }
                }

                return resultsList;
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
        Optional<Survey> optionalSurvey = surveyRepository.findById(surveyId);
        if (optionalSurvey.isPresent()) {
            Survey survey = optionalSurvey.get();
            if (survey.getOwner().getEmail().equals(email)) {
                Optional<SurveyFilter> optionalSurveyFilter = surveyFilterRepository.findBySurvey(survey);
                if (optionalSurveyFilter.isPresent()) {
                    SurveyFilter surveyFilter = optionalSurveyFilter.get();
                    List<User> userList = personalDataRepository.findAllByParameters(surveyFilter.getAgeMin(),
                            surveyFilter.getAgeMax(), surveyFilter.getGenders(), surveyFilter.getEducations(),
                            surveyFilter.getSizeOfTheHometownMin(), surveyFilter.getSizeOfTheHometownMax(),
                            surveyFilter.getSizeOfTownMin(), surveyFilter.getSizeOfTownMax(),
                            surveyFilter.getGrossEarningsMin(), surveyFilter.getGrossEarningsMax(),
                            surveyFilter.getLaborSectors(), surveyFilter.getMaritalStatuses());
                    userList.remove(survey.getOwner());
                    return userList;
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public List<Query> getRespQueries(Long pId, Long sId, String email) {
        Optional<PersonalData> optionalPersonalData = personalDataRepository.findById(pId);
        if (optionalPersonalData.isPresent()) {
            PersonalData personalData = optionalPersonalData.get();
            Optional<Survey> optionalSurvey = surveyRepository.findById(sId);
            if (optionalSurvey.isPresent() && personalData.getUser().getEmail().equals(email)) {
                Survey survey = optionalSurvey.get();

                if (survey.getResults().stream().noneMatch(r -> r.getUser() == personalData.getUser())) {
                    return survey.getQueries();
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public ResponseEntity<Resource> getCSV(Long surveyId, String email) throws IOException {
        //getSurveyResults from DB
        List<SurveyResult>surveyResults = getResultsFromDB(surveyId,email);

        File fileCSV = new File("result.csv");
        PrintWriter out = new PrintWriter(fileCSV);

        for (SurveyResult v : surveyResults) {
            out.println(v.getResponses());
        }
        out.close();

        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(fileCSV.getPath()

        )));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);
    }
}
