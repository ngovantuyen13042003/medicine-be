package nvt.st.springjwtauthentication.service;

import nvt.st.springjwtauthentication.entity.User;

public interface UserService {
    User findByEmail(String email);
    Boolean existsByEmail(String email);
    User saveOrUpdate(User user);
}
