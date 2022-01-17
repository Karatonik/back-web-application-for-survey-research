package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.kalksztejn.survey.models.Award;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyDTO;
import pl.mateusz.kalksztejn.survey.models.payload.response.UserInfo;
import pl.mateusz.kalksztejn.survey.services.interfaces.MailService;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    UserService userService;
    MailService mailService;

    @Autowired
    public UserController(UserService userService, MailService mailService) {

        this.userService = userService;
        this.mailService = mailService;
    }

    @GetMapping("{email}")
    public ResponseEntity<List<SurveyDTO>> getUserSurvey(@PathVariable @NotBlank String email) {
        return new ResponseEntity<>(userService.getUserSurvey(email)
                .stream().map(SurveyDTO::new).collect(Collectors.toList())
                , HttpStatus.OK);
    }

    @GetMapping("points/{email}")
    public ResponseEntity<Long> getPoints(@PathVariable @NotBlank String email) {
        return new ResponseEntity<>(userService.getUserPoints(email), HttpStatus.OK);
    }

    @GetMapping("awards/{email}")
    public ResponseEntity<List<Award>> getUserAwards(@PathVariable @NotBlank String email) {
        return new ResponseEntity<>(userService.getUserAwards(email), HttpStatus.OK);
    }

    @GetMapping("admin/{adminEmail}")
    public ResponseEntity<List<UserInfo>> getAllUsers(@PathVariable String adminEmail) {
        return new ResponseEntity<>(userService.getAllUsers(adminEmail), HttpStatus.OK);
    }

    @PutMapping("increase/{adminEmail}/{userEmail}")
    public ResponseEntity<Boolean> increasePermissionsForUser(@PathVariable String adminEmail
            , @PathVariable String userEmail) {
        return new ResponseEntity<>(userService.increasePermissionsForUser(adminEmail, userEmail), HttpStatus.OK);
    }

    @PutMapping("reduce/{adminEmail}/{userEmail}")
    public ResponseEntity<Boolean> reducePermissionsForUser(@PathVariable String adminEmail
            , @PathVariable String userEmail) {
        return new ResponseEntity<>(userService.reducePermissionsForUser(adminEmail, userEmail), HttpStatus.OK);
    }
}
