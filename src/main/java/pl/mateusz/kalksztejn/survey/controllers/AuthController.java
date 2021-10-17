package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mateusz.kalksztejn.survey.models.payload.request.LoginRequest;
import pl.mateusz.kalksztejn.survey.models.payload.request.SignUpRequest;
import pl.mateusz.kalksztejn.survey.services.interfaces.AuthService;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 7200)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthService authenticationManager;

    public AuthController(AuthService authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationManager.authenticate(loginRequest);
    }
    @PostMapping("reg")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        return authenticationManager.register(signUpRequest);
    }
}
