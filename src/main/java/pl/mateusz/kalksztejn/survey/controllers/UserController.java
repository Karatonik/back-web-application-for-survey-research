package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyDTO;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping ("/{key}")
    public ResponseEntity<Boolean> confirmation(@PathVariable @NotBlank String key) {
        return new ResponseEntity<>(userService.confirmation(key)
                , HttpStatus.OK);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Boolean> deleteWithKey(@PathVariable @NotBlank String key) {
        return new ResponseEntity<>(userService.deleteWithKey(key)
                , HttpStatus.OK);
    }

    @PutMapping("/{key}/{newPassword}")
    public ResponseEntity<Boolean> changePassword(@PathVariable @NotBlank String key
            , @PathVariable @NotBlank String newPassword) {
        return new ResponseEntity<>(userService.changePassword(key, newPassword)
                , HttpStatus.OK);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<SurveyDTO>> getUserSurvey(@PathVariable @NotBlank String email) {
        return new ResponseEntity<>(userService.getUserSurvey(email)
                .stream().map(SurveyDTO::new).collect(Collectors.toList())
                , HttpStatus.OK);
    }
}
