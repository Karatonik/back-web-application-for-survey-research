package pl.mateusz.kalksztejn.survey.controllers;

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

    AuthService authenticationManager;

    public AuthController(AuthService authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody  LoginRequest loginRequest) {
        return authenticationManager.authenticate(loginRequest);
    }
    @PostMapping("reg")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        return authenticationManager.register(signUpRequest);
    }
    @PutMapping("sk")
    public ResponseEntity<Boolean> sendKey(@RequestBody String email){
        return new ResponseEntity<>(authenticationManager.sendKeyToChangePassword(email),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Boolean> confirmation(@RequestBody String key) {
        return new ResponseEntity<>(authenticationManager.confirmation(key)
                , HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> deleteWithKey(@RequestBody String key) {
        return new ResponseEntity<>(authenticationManager.deleteWithKey(key)
                , HttpStatus.OK);
    }

    @PutMapping("/{key}/{nPass}")
    public ResponseEntity<Boolean> changePassword(@PathVariable @NotBlank String key
            , @PathVariable @NotBlank String nPass) {
        return new ResponseEntity<>(authenticationManager.changePassword(key, nPass)
                , HttpStatus.OK);
    }
}
