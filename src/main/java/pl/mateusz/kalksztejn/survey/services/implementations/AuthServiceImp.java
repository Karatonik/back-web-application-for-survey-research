package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.jwt.JwtUtils;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.UserDetailsImp;
import pl.mateusz.kalksztejn.survey.models.payload.request.LoginRequest;
import pl.mateusz.kalksztejn.survey.models.payload.request.SignUpRequest;
import pl.mateusz.kalksztejn.survey.models.payload.response.JwtResponse;
import pl.mateusz.kalksztejn.survey.models.payload.response.MessageResponse;
import pl.mateusz.kalksztejn.survey.repositorys.UserRepository;
import pl.mateusz.kalksztejn.survey.services.interfaces.AuthService;
import pl.mateusz.kalksztejn.survey.services.interfaces.MailService;

@Service
public class AuthServiceImp implements AuthService {

    AuthenticationManager authenticationManager;

    UserRepository userRepository;

    MailService mailService;

    PasswordEncoder encoder;

    JwtUtils jwtUtils;

    @Autowired
    public AuthServiceImp(AuthenticationManager authenticationManager, UserRepository userRepository
            , MailService mailService, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public ResponseEntity<?> authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        //System.out.println(authentication.getName());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getPoints(),userDetails.getEmail(),userDetails.isActivated()));
    }

    @Override
    public ResponseEntity<?> register(SignUpRequest signUpRequest) {
        if (userRepository.existsById(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already taken!"));
        }
        // Create new user's account
        User user = new User(signUpRequest.getEmail(),encoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);

        //wysyłanie maila z kodem potwierdzenia
        try {
            mailService.sendConfirmation(user.getEmail());
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
