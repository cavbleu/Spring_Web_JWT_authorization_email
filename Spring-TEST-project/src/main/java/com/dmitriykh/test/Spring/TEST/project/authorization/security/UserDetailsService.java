package com.dmitriykh.test.Spring.TEST.project.authorization.security;

import com.dmitriykh.test.Spring.TEST.project.authorization.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private final IUserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(UserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
