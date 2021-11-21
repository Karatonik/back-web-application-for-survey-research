package pl.mateusz.kalksztejn.survey.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.hash.Hashing;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity(name = "Person")
public class User {
    @Id
    private String email;

    private String password;

    private long points;

    private boolean activated;

    @JsonIgnore
    private String userKey;

    @OneToMany(mappedBy = "owner")
    private List<Survey> userSurveyList;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.activated = false;
        this.userKey = Hashing.sha256()
                .hashString(String.valueOf(hashCode()), StandardCharsets.UTF_8)
                .toString();
        this.points = 0;
        this.userSurveyList = new ArrayList<>();
    }
    public long addPoints(long points){
        return this.points+=points;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword(), new Date(System.currentTimeMillis()));
    }

    public void getNewKey() {
        this.userKey = Hashing.sha256()
                .hashString(String.valueOf(hashCode()), StandardCharsets.UTF_8)
                .toString();
    }
}
