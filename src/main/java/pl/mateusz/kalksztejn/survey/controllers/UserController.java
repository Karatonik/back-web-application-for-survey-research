package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.kalksztejn.survey.models.dto.AwardDTO;
import pl.mateusz.kalksztejn.survey.models.dto.SurveyDTO;
import pl.mateusz.kalksztejn.survey.services.interfaces.MailService;
import pl.mateusz.kalksztejn.survey.services.interfaces.UserService;

import javax.mail.MessagingException;
import javax.validation.constraints.NotBlank;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
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

    @GetMapping("p/{email}")
    public ResponseEntity<Long> getPoints(@PathVariable @NotBlank String email) {
        return new ResponseEntity<>(userService.getUserPoints(email), HttpStatus.OK);
    }

    @PutMapping("{email}")
    public ResponseEntity<Boolean> sendRewardEmail(@PathVariable @NotBlank String email) throws MessagingException {
        return new ResponseEntity<Boolean>(mailService.sendRewardMail(email), HttpStatus.OK);
    }

    @GetMapping(value = "r/{email}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Resource> getMascot(@PathVariable @NotBlank String email) throws IOException {
        return userService.getMascot(email);
    }

    @GetMapping("a/{email}")
    public ResponseEntity<List<AwardDTO>> getUserAwards(@PathVariable @NotBlank String email) {
        return new ResponseEntity<>(userService.getUserAwards(email).stream().map(AwardDTO::new)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}
