package pl.mateusz.kalksztejn.survey.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mateusz.kalksztejn.survey.models.Survey;
import pl.mateusz.kalksztejn.survey.models.User;
import pl.mateusz.kalksztejn.survey.models.enums.AccountType;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

   private String email;

   private boolean activated;

   private AccountType accountType;

   private List<Long> userSurveysIds;

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.activated =user.isActivated();
        this.accountType= user.getAccountType();
        this.userSurveysIds = user.getUserSurveyList().stream()
                .map(Survey::getId).collect(Collectors.toList());
    }
}
