package pl.mateusz.kalksztejn.survey.models.payload.response;

import lombok.*;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.enums.AccountType;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class UserInfo {

    String email;

    private long points;

    private boolean activated;

    private AccountType accountType;

    public UserInfo(User user) {
        this.email = user.getEmail();
        this.points = user.getPoints();
        this.activated = user.isActivated();
        this.accountType = user.getAccountType();
    }
}
