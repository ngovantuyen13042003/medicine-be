package nvt.st.springjwtauthentication.service;

import nvt.st.springjwtauthentication.entity.Erole;
import nvt.st.springjwtauthentication.entity.Roles;

import java.util.Optional;

public interface RoleService {
    Optional<Roles> findByRoleName(Erole name);
}
