package nvt.st.springjwtauthentication.service.impl;

import nvt.st.springjwtauthentication.Repository.UserRepository;
import nvt.st.springjwtauthentication.entity.User;
import nvt.st.springjwtauthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User saveOrUpdate(User user) {
        return userRepository.save(user);
    }
}
