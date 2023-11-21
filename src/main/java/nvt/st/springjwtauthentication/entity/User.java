package nvt.st.springjwtauthentication.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(name = "email", unique = true)
    @Email(message = "Email is invalid!")
    @NotEmpty(message = "Email should be not empty!")
    private String email;

    @NotEmpty(message = "Password should not be empty!")
    @Length(min = 6, message = "Password must be at least 6 characters!")
    private  String password;



    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "idRole"))
    private Set<Roles> roles = new HashSet<>();


}
