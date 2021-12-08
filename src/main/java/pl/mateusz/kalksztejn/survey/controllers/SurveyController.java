package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.dto.QueryDTO;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyDTO;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyResultDTO;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.MailService;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyService;

import javax.mail.MessagingException;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/survey")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SurveyController {

    SurveyService surveyService;
    ModelMapper modelMapper;
    MailService mailService;

    @Autowired
    public SurveyController(SurveyService surveyService, ModelMapper modelMapper,MailService mailService) {
        this.surveyService = surveyService;
        this.modelMapper = modelMapper;
        this.mailService = mailService;
    }

    @GetMapping("/{id}/{email}")
    public ResponseEntity<SurveyDTO> get(@PathVariable @NotBlank Long id
            , @PathVariable @NotBlank String email) {
        return new ResponseEntity<>(new SurveyDTO(surveyService.get(id, email))
                , HttpStatus.OK);
    }

    @PostMapping("/{name}/{email}")
    public ResponseEntity<SurveyDTO> set(@PathVariable @NotBlank String name
            , @PathVariable @NotBlank String email) {
        return new ResponseEntity<>(new SurveyDTO(surveyService.set(name, email))
                , HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{email}")
    public ResponseEntity<Boolean> delete(@PathVariable @NotBlank Long id
            , @PathVariable @NotBlank String email) {
        return new ResponseEntity<>(surveyService.delete(id, email)
                , HttpStatus.OK);
    }

    @GetMapping("/res/{id}/{email}")
    public ResponseEntity<List<SurveyResultDTO>> getResults(@PathVariable @NotBlank Long id
            , @PathVariable @NotBlank String email) {
        return new ResponseEntity<>(surveyService.getSurveyResults(id, email)
                .stream().map(SurveyResultDTO::new).collect(Collectors.toList())
                , HttpStatus.OK);
    }

    @GetMapping("/que/{id}")
    public ResponseEntity<List<QueryDTO>> getQueries(@PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(surveyService.getQueries(id)
                .stream().map(QueryDTO::new).collect(Collectors.toList())
                , HttpStatus.OK);
    }
    @GetMapping("/{email}")
    public ResponseEntity<List<SurveyDTO>> getAllByEmail(@PathVariable @NotBlank String email){
        return  new ResponseEntity<>(surveyService.getAllByEmail(email).stream().map(SurveyDTO::new).collect(Collectors.toList())
                ,HttpStatus.OK);
    }

    @PutMapping("/que/{id}/{email}")
    public ResponseEntity<List<QueryDTO>> setQueries (@PathVariable @NotBlank String email,@PathVariable @NotBlank Long id
            , @RequestBody List<QueryDTO> queryDTOList){
        return new ResponseEntity<>(surveyService.setQueries(email,id
                        ,queryDTOList.stream().map(queryDTO -> modelMapper.queryMapper(queryDTO)).collect(Collectors.toList()))
                .stream().map(QueryDTO::new).collect(Collectors.toList())
                , HttpStatus.OK);
    }
    @GetMapping("inv/{id}/{email}")
    public ResponseEntity<Boolean> inviteRespondents(@PathVariable @NotBlank String email,@PathVariable @NotBlank Long id) throws MessagingException {
        return new ResponseEntity<>(mailService.sendMailsWithSurvey(surveyService
                .getRespondentsList(email,id).stream().map(User::getEmail).collect(Collectors.toList()), id)
                ,HttpStatus.OK);
    }
    @GetMapping("resp/{pId}/{email}/{sId}")
    public ResponseEntity<List<QueryDTO>> getRespQueries(@PathVariable @NotBlank Long pId,@PathVariable @NotBlank Long sId,@PathVariable @NotBlank String email ){
        return new ResponseEntity<>(surveyService.getRespQueries(pId, sId, email)
                .stream().map(QueryDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }
}
