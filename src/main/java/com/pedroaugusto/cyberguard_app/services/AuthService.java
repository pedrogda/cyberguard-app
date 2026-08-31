package com.pedroaugusto.cyberguard_app.services;

import com.pedroaugusto.cyberguard_app.exception.InvalidPasswordException;
import com.pedroaugusto.cyberguard_app.exception.UserNotFoundException;
import com.pedroaugusto.cyberguard_app.model.User;
import com.pedroaugusto.cyberguard_app.repository.UserRepository;
import com.pedroaugusto.cyberguard_app.security.JwtService;
import dto.LoginRequest;
import dto.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        return userRepository.save(user);
    }

    public String login(LoginRequest request) {

        User user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(UserNotFoundException::new);



        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {

            throw new InvalidPasswordException();
        }


        return jwtService.generateToken(
                user.getUsername()
        );
    }
}
