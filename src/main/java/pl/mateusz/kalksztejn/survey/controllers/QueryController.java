package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.kalksztejn.survey.models.dto.QueryDTO;
import pl.mateusz.kalksztejn.survey.services.implementations.mappers.ModelMapper;
import pl.mateusz.kalksztejn.survey.services.interfaces.QueryService;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("api/que")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class QueryController {

    QueryService queryService;
    ModelMapper modelMapper;

    @Autowired
    public QueryController(QueryService queryService, ModelMapper modelMapper) {
        this.queryService = queryService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<QueryDTO> setQuery(@RequestBody QueryDTO queryDTO) {
        return new ResponseEntity<>(new QueryDTO(queryService
                .setQuery(modelMapper.queryMapper(queryDTO)))
                , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QueryDTO> getQuery(@PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(new QueryDTO(queryService.getQuery(id))
                , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteQuery(@PathVariable @NotBlank Long id) {
        return new ResponseEntity<>(queryService.deleteQuery(id)
                , HttpStatus.OK);
    }
}

