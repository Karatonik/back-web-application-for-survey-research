package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.kalksztejn.survey.models.payload.request.LoginRequest;
import pl.mateusz.kalksztejn.survey.models.payload.request.SignUpRequest;
import pl.mateusz.kalksztejn.survey.services.interfaces.AuthService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@CrossOrigin(origins = "*", maxAge = 7200)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticate(loginRequest);
    }

    @PostMapping("reg")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        return authService.register(signUpRequest);
    }

    @PutMapping
    public ResponseEntity<Boolean> sendKey(@RequestBody String email) {
        return new ResponseEntity<>(authService.sendKeyToChangePassword(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Boolean> confirmation(@RequestBody String key) {
        return new ResponseEntity<>(authService.confirmation(key)
                , HttpStatus.OK);
    }

    @PutMapping("/{key}/{nPass}")
    public ResponseEntity<Boolean> changePassword(@PathVariable @NotBlank String key
            , @PathVariable @NotBlank String nPass) {
        return new ResponseEntity<>(authService.changePassword(key, nPass)
                , HttpStatus.OK);
    }
}
