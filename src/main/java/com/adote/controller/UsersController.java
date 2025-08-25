package com.adote.controller;

import com.adote.domain.User;
import com.adote.dto.UserDTOs.*;
import com.adote.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserService users;
    public UsersController(UserService users){ this.users = users; }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateUserRequest req){
        User u = users.create(req.name(), req.email(), req.password());
        return ResponseEntity.status(201).body(u);
    }

    @PatchMapping
    public ResponseEntity<?> updateMe(@RequestBody UpdateUserRequest req, Authentication auth){
        // auth.getName() é o email
        var u = users.findByEmail(auth.getName()).orElseThrow();
        if(req.name()!=null) u.setName(req.name());
        return ResponseEntity.ok(users.save(u));
    }
}
