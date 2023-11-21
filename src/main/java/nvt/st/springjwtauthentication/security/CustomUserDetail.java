package nvt.st.springjwtauthentication.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import nvt.st.springjwtauthentication.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CustomUserDetail implements UserDetails {
    private Long idUser;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    // from information user mapping to information CustomUserDetails
    public static CustomUserDetail mapUserToUserDetails(User user){
        // get all authorities from user
        // java 8
        List<GrantedAuthority> listAuthorities = user.getRoles().stream()
                .map(roles -> new SimpleGrantedAuthority(roles.getRoles().name()))
                .collect(Collectors.toList());


        return  new CustomUserDetail(
                user.getIdUser(),
                user.getEmail(),
                user.getPassword(),
                listAuthorities
        );
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
}
