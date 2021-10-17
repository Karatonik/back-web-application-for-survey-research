package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestAuthController {

    //jwt pass
    @GetMapping("api/pass")
    public String pass() {
        return "pass";
    }
    //jwt Unauthorized
    @GetMapping("api/fail")
    public String denied() {
        return "fail";
    }
}
