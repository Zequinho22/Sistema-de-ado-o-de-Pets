package com.adote.service;

import com.adote.domain.User;
import com.adote.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public UserService(UserRepository users, PasswordEncoder encoder) {
        this.users = users; this.encoder = encoder;
    }

    public User create(String name, String email, String rawPassword){
        var u = new User();
        u.setName(name); u.setEmail(email);
        u.setPasswordHash(encoder.encode(rawPassword));
        return users.save(u);
    }

    public Optional<User> findByEmail(String email){ return users.findByEmail(email); }
    public User save(User u){ return users.save(u); }
}
