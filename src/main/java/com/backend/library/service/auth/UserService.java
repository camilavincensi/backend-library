package com.backend.library.service.auth;

import com.backend.library.entity.Role;
import com.backend.library.entity.User;
import com.backend.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public User register(User user) {

        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }

        user.setPassword(
                encoder.encode(user.getPassword())
        );

        return repository.save(user);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
}