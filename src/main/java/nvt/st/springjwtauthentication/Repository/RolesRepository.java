package nvt.st.springjwtauthentication.Repository;

import nvt.st.springjwtauthentication.entity.Erole;
import nvt.st.springjwtauthentication.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {

    Optional<Roles> findByRoles(Erole roleName);
}
