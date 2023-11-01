package com.dmitriykh.test.Spring.TEST.project.authorization.token;

import com.dmitriykh.test.Spring.TEST.project.authorization.user.User;

import java.util.Optional;

public interface IVerificationTokenService {

    String validateToken(String token);
    void saveVerificationTokenForUser(User user, String token);
    Optional<VerificationToken> findByToken(String token);

    void deleteUserToken(Long id);
}
