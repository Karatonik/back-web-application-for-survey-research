package pl.mateusz.kalksztejn.survey.models.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {
    boolean activated;
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
    private String email;
    @Size(min = 8)
    private String password;


    public SignUpRequest(String email, String password) {
        this.email = email;
        this.password = password;
        this.activated = false;
    }
}
