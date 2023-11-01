package com.dmitriykh.test.Spring.TEST.project.authorization.registration.password;

import com.dmitriykh.test.Spring.TEST.project.authorization.repositories.IUserRepository;
import com.dmitriykh.test.Spring.TEST.project.authorization.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenService implements IPasswordResetTokenSevice{
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

//    public PasswordResetTokenService (PasswordResetTokenRepository passwordResetTokenRepository, UserRepository userRepository)
//    {this.passwordResetTokenRepository = passwordResetTokenRepository;
//    this.userRepository = userRepository;}
    @Override
    public String validatePasswordResetToken(String theToken) {
        Optional<PasswordResetToken> passwordResetToken = passwordResetTokenRepository.findByToken(theToken);
        if (passwordResetToken.isEmpty()){
            return "invalid";
        }
        Calendar calendar = Calendar.getInstance();
        if ((passwordResetToken.get().getExpirationTime().getTime()-calendar.getTime().getTime())<= 0){
            return "expired";
        }
        return "valid";
    }

    @Override
    public Optional<User> findUserByPasswordResetToken(String theToken) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(theToken).get().getUser());
    }

    @Override
    public void resetPassword(User theUser, String password) {
//        theUser.setPassword(password);
        theUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(theUser);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String passwordResetToken) {
        PasswordResetToken resetToken = new PasswordResetToken(passwordResetToken, user);
        passwordResetTokenRepository.save(resetToken);
    }
}
