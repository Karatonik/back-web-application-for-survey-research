package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.kalksztejn.survey.models.dto.PersonalDataDTO;
import pl.mateusz.kalksztejn.survey.models.enums.Education;
import pl.mateusz.kalksztejn.survey.models.enums.Gender;
import pl.mateusz.kalksztejn.survey.models.enums.LaborSector;
import pl.mateusz.kalksztejn.survey.models.enums.MaritalStatus;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.PersonalDataService;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("api/pd")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PersonalDataController {

    PersonalDataService personalDataService;
    ModelMapper modelMapper;

    @Autowired
    public PersonalDataController(PersonalDataService personalDataService, ModelMapper modelMapper) {
        this.personalDataService = personalDataService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<PersonalDataDTO> set(@RequestBody PersonalDataDTO personalDataDTO) {
        return new ResponseEntity<>(new PersonalDataDTO(personalDataService
                .set(modelMapper.personalDataMapper(personalDataDTO)))
                , HttpStatus.OK);
    }



    /*@PostMapping("/{age}/{gender}/{sizeOfTheHometown}/{sizeOfTown}/{grossEarnings}" +
            "/{education}/{laborSector}/{maritalStatus}/{email}")
    public ResponseEntity<PersonalDataDTO> set(@PathVariable Long age, @PathVariable Gender gender
            , @PathVariable Long sizeOfTheHometown, @PathVariable Long sizeOfTown
            , @PathVariable Double grossEarnings, @PathVariable Education education
            , @PathVariable LaborSector laborSector, @PathVariable MaritalStatus maritalStatus
            , @PathVariable String email) {
        return new ResponseEntity<>(new PersonalDataDTO(personalDataService
                .set(age, gender, sizeOfTheHometown, sizeOfTown, grossEarnings
                        , education, laborSector, maritalStatus, email))
                , HttpStatus.OK);
    }*/

    @GetMapping("/{id}")
    public ResponseEntity<PersonalDataDTO> get(@PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(new PersonalDataDTO(personalDataService.get(id))
                , HttpStatus.OK);
    }

    @GetMapping("/e/{email}")
    public ResponseEntity<PersonalDataDTO> getByUser(@PathVariable String email) {
        return new ResponseEntity<>(new PersonalDataDTO(personalDataService.getByUser(email))
                , HttpStatus.OK);
    }
}
