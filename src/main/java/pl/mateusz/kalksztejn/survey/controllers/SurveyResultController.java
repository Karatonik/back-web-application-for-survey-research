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
    public ResponseEntity<SurveyResultDTO> get(@PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(new SurveyResultDTO(surveyResultService.get(id))
                , HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(surveyResultService.delete(id)
                , HttpStatus.OK);
    }
    @PostMapping("/{id}")
    public ResponseEntity<SurveyResultDTO> set(@PathVariable @NotBlank Long id,@RequestBody SurveyResultDTO resultDTO) {
        return new ResponseEntity<>(new SurveyResultDTO(surveyResultService.set(
                modelMapper.surveyResultMapper(resultDTO),id))
                , HttpStatus.OK);
    }
}
