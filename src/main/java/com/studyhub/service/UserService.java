package com.studyhub.service;

import com.studyhub.domain.User;

public interface UserService {
    void registerUser(User user);
    User findUserByEmail(String email);
    boolean checkBusinessNumberExists(String businessNumber);
    User validateLogin(String email, String password);

    User findById(Long id);

    void changePassword(Long userId, String currentPassword, String newPassword);
    void changePhone(Long userId, String newPhone);
}