package pl.mateusz.kalksztejn.survey.services.interfaces;

import org.springframework.http.ResponseEntity;
import pl.mateusz.kalksztejn.survey.models.payload.request.LoginRequest;
import pl.mateusz.kalksztejn.survey.models.payload.request.SignUpRequest;

public interface AuthService {

    ResponseEntity<?> authenticate(LoginRequest loginRequest);

    ResponseEntity<?> register(SignUpRequest signUpRequest);
}
