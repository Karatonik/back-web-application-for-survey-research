package pl.mateusz.kalksztejn.survey.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@Entity(name = "Person")
@EqualsAndHashCode
public class User {
    @Id
   private String email;

   private String password;

   private long points;

   private boolean activated;

   @OneToMany
   private List<Payment> payments;

    @OneToMany(mappedBy = "owner")
   private List<Survey> userSurveyList;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.activated=false;
        this.points=0;
        this.userSurveyList=new ArrayList<>();
        this.payments = new ArrayList<>();
    }
}
