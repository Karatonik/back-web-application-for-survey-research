package pl.mateusz.kalksztejn.survey.models.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mateusz.kalksztejn.survey.models.enums.AccountType;

@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private Long points;
    private String email;
    private boolean activated;
    private AccountType accountType;

    public JwtResponse(String token,Long points, String email,boolean activated, AccountType accountType) {
        this.token = token;
        this.points = points;
        this.email = email;
        this.activated = activated;
        this.accountType = accountType;
    }
}
