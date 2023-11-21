package nvt.st.springjwtauthentication.service.impl;

import nvt.st.springjwtauthentication.Repository.RolesRepository;
import nvt.st.springjwtauthentication.entity.Erole;
import nvt.st.springjwtauthentication.entity.Roles;
import nvt.st.springjwtauthentication.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public Optional<Roles> findByRoleName(Erole name) {
        return rolesRepository.findByRoles(name);
    }
}
