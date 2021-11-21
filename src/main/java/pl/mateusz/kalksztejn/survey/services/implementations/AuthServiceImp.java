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

import java.util.Optional;

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
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        //System.out.println(authentication.getName());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getPoints(),userDetails.getEmail(),userDetails.isActivated()));
        }catch (Exception e){
            e.getStackTrace();
        }
        return ResponseEntity.ok(new JwtResponse(null,null,null,false));
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

    @Override
    public boolean sendKeyToChangePassword(String email) {
        Optional<User> optionalUser = userRepository.findById(email);
        if(optionalUser.isPresent()){
            try {
                mailService.sendResetPassword(optionalUser.get().getEmail());
            }catch (Exception e){
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean confirmation(String key) {
        Optional<User> optionalUser = userRepository.findByUserKey(key);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setActivated(true);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteWithKey(String key) {
        Optional<User> optionalUser = userRepository.findByUserKey(key);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            //survey
           // surveyRepository.deleteAll(surveyRepository.findAllByOwner(user));
            //todo
            //user
            userRepository.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean changePassword(String key, String newPassword) {
        Optional<User> optionalUser = userRepository.findByUserKey(key);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getNewKey();
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
            return  true;
        }
        return false;
    }
}
