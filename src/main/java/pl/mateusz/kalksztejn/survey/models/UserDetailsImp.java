package pl.mateusz.kalksztejn.survey.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserDetailsImp implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String email;

    private Long points;

    @JsonIgnore
    private String password;

    private boolean activated;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImp(String email,Long points, String password, boolean activated) {
        this.email = email;
        this.password = password;
        this.points = points;
        this.authorities = new HashSet<>();
        this.activated=activated;
    }
    public static UserDetailsImp build(User user) {

        return new UserDetailsImp(
                user.getEmail(), user.getPoints(),
                user.getPassword(),user.isActivated());
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImp user = (UserDetailsImp) o;
        return Objects.equals(email, user.email);
    }

}
