package pl.mateusz.kalksztejn.survey.models.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {
    private String token;
    private final String type = "Bearer";
    private Long points;
    private String email;
    private boolean activated;

    public JwtResponse(String token,Long points, String email,boolean activated) {
        this.token = token;
        this.points = points;
        this.email = email;
        this.activated = activated;
    }
}
