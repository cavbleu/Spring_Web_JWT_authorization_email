package com.dmitriykh.test.Spring.TEST.project.authorization.services;

import com.dmitriykh.test.Spring.TEST.project.authorization.repositories.IUserRepository;
import com.dmitriykh.test.Spring.TEST.project.authorization.user.User;
import com.dmitriykh.test.Spring.TEST.project.authorization.registration.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface IUserService{
    List<User> getAllUsers();
    User findByEmail(String email);
    User registerUser(RegistrationRequest registration);

    void updateUser(Long id, String firstName, String lastName, String email);

    void deleteUser(Long id);

    Optional<User> findById(Long id);

}
