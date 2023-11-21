package nvt.st.springjwtauthentication.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRole;
    @Enumerated(EnumType.STRING)
    private Erole roles;
}
