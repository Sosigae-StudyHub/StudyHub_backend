package com.studyhub.service;

import com.studyhub.dao.UserDao;
import com.studyhub.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(User user) {
        userDao.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public boolean checkBusinessNumberExists(String businessNumber) {
        return userDao.existsByBusinessNumber(businessNumber);
    }

    @Override
    public User validateLogin(String email, String password) {
        User user = userDao.findByEmail(email);
        if (user == null) return null;

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}
