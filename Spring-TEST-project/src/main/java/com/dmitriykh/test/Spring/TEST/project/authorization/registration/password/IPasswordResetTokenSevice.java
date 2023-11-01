package com.dmitriykh.test.Spring.TEST.project.authorization.registration.password;

import com.dmitriykh.test.Spring.TEST.project.authorization.user.User;

import java.util.Optional;

public interface IPasswordResetTokenSevice {

    String validatePasswordResetToken(String theToken);

    Optional<User> findUserByPasswordResetToken(String theToken);

    void resetPassword(User theUser, String password);

    void createPasswordResetTokenForUser(User user, String passwordResetToken);
}
