package com.assignment.domain.users.service;

import com.assignment.domain.users.domain.Users;
import com.assignment.domain.users.dto.CreateUserRequest;
import com.assignment.domain.users.dto.LoginRequest;
import com.assignment.domain.users.repository.UsersRepository;
import com.assignment.global.security.CustomUserDetails;
import com.assignment.global.security.dto.UserInfo;
import com.assignment.global.security.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public void save(CreateUserRequest request) {
        Optional<Users> users = usersRepository.findByEmail(request.email());
        if(users.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        Users user = Users.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .username(request.username())
                .role("USER")
                .build();
        usersRepository.save(user);
    }

    public String login(LoginRequest request) {
        Optional<Users> optionalUser = usersRepository.findByEmail(request.email());
        if(optionalUser.isEmpty()) {
            throw new RuntimeException("cannot Login");
        }
        Users user = optionalUser.get();
        boolean match = passwordEncoder.matches(request.password(), user.getPassword());
        if(!match) {
            throw new RuntimeException("cannot Login");
        }

        return jwtTokenService.generateAccessToken(user.getId(), user.getRole());
    }
}
