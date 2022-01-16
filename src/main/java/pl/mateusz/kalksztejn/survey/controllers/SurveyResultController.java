package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.kalksztejn.survey.models.SurveyResult;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyResultDTO;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyResultService;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("api/res")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SurveyResultController {

    SurveyResultService surveyResultService;
    ModelMapper modelMapper;

    @Autowired
    public SurveyResultController(SurveyResultService surveyResultService, ModelMapper modelMapper) {
        this.surveyResultService = surveyResultService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyResultDTO> getSurveyResult(@PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(new SurveyResultDTO(surveyResultService.getSurveyResult(id))
                , HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSurveyResult(@PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(surveyResultService.deleteSurveyResult(id)
                , HttpStatus.OK);
    }
    @PostMapping("/{id}")
    public ResponseEntity<SurveyResultDTO> setSurveyResult(@PathVariable @NotBlank Long id,@RequestBody SurveyResultDTO resultDTO) {
        return new ResponseEntity<>(new SurveyResultDTO(surveyResultService.setSurveyResult(
                modelMapper.surveyResultMapper(resultDTO),id))
                , HttpStatus.OK);
    }
}
