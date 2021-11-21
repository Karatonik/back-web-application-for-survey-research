package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyFilterDTO;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.SurveyFilterService;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("api/fil")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SurveyFilterController {

    SurveyFilterService surveyFilterService;
    ModelMapper modelMapper;

    @Autowired
    public SurveyFilterController(SurveyFilterService surveyFilterService, ModelMapper modelMapper) {
        this.surveyFilterService = surveyFilterService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<SurveyFilterDTO> set(@RequestBody SurveyFilterDTO surveyFilterDTO) {
        return new ResponseEntity<>(new SurveyFilterDTO(surveyFilterService
                .set(modelMapper.surveyFilterMapper(surveyFilterDTO)))
                , HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<SurveyFilterDTO> edit(@RequestBody SurveyFilterDTO surveyFilterDTO) {
        return new ResponseEntity<>(new SurveyFilterDTO(surveyFilterService
                .edit(modelMapper.surveyFilterMapper(surveyFilterDTO)))
                , HttpStatus.OK);
    }

    @PutMapping("/{surveyId}/{id}")
    public ResponseEntity<Boolean> addSurveyToFilter(@PathVariable @NotBlank Long surveyId
            , @PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(surveyFilterService.addSurveyToFilter(surveyId, id)
                , HttpStatus.OK);
    }


    @DeleteMapping("/{id}/{email}")
    public ResponseEntity<Boolean> delete(@PathVariable @NotBlank Long id
            , @PathVariable @NotBlank String email) {
        return new ResponseEntity<>(surveyFilterService.delete(id, email)
                , HttpStatus.OK);
    }
    @GetMapping("{surveyId}/{email}")
    public ResponseEntity<SurveyFilterDTO>getBySurveyId(@PathVariable @NotBlank Long surveyId ,@PathVariable @NotBlank String email){
        return new ResponseEntity<>(new SurveyFilterDTO(surveyFilterService.getBySurveyId(surveyId,email)),HttpStatus.OK);
    }
}
