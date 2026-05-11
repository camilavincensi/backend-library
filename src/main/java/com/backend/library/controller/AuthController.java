package com.backend.library.controller;

import com.backend.library.dto.auth.AuthRequest;
import com.backend.library.dto.auth.AuthResponse;
import com.backend.library.entity.Role;
import com.backend.library.entity.User;
import com.backend.library.service.auth.JwtService;
import com.backend.library.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        User user = userService.findByUsername(request.getUsername());

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken =
                jwtService.generateToken(
                        user.getUsername(),
                        user.getRole()
                );

        System.out.println(user.getRole());
        System.out.println(user.getPassword());
        System.out.println(
                encoder.matches(
                        request.getPassword(),
                        user.getPassword()
                )
        );


        return new AuthResponse(accessToken);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody AuthRequest request
    ) {
        try {

            User user = new User();

            user.setUsername(request.getUsername());

            // senha crua aqui
            user.setPassword(request.getPassword());

            user.setRole(Role.ADMIN);

            return ResponseEntity.ok(
                    userService.register(user)
            );

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }
}