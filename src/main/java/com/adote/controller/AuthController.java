package com.adote.controller;

import com.adote.dto.AuthDTOs.*;
import com.adote.security.JwtService;
import com.adote.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final UserService users; private final JwtService jwt; private final PasswordEncoder encoder;
    public AuthController(UserService u, JwtService j, PasswordEncoder e){ this.users=u; this.jwt=j; this.encoder=e; }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req){
        var opt = users.findByEmail(req.email());
        if(opt.isPresent() && encoder.matches(req.password(), opt.get().getPasswordHash())){
            var u = opt.get();
            var token = jwt.generate(u.getId(), u.getEmail());
            return ResponseEntity.ok(new LoginResponse(token, u.getId(), u.getName(), u.getEmail()));
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
