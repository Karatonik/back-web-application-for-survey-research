package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.kalksztejn.survey.models.dto.QueryDTO;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyDTO;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyResultDTO;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyService;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/survey")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SurveyController {

    SurveyService surveyService;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
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
}
