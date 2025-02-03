package com.assignment.domain.users.service;

import com.assignment.domain.users.domain.Users;
import com.assignment.domain.users.dto.CreateUserRequest;
import com.assignment.domain.users.dto.LoginRequest;
import com.assignment.domain.users.repository.UsersRepository;
import com.assignment.global.exception.BusinessException;
import com.assignment.global.security.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.assignment.global.exception.errortype.UserErrorCode.CANNOT_LOGIN;
import static com.assignment.global.exception.errortype.UserErrorCode.USER_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public void save(CreateUserRequest request) {
        Optional<Users> users = usersRepository.findByEmail(request.email());
        if (users.isPresent()) {
            throw new BusinessException(USER_ALREADY_EXIST);
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
        if (optionalUser.isEmpty()) {
            throw new BusinessException(CANNOT_LOGIN);
        }
        Users user = optionalUser.get();
        boolean match = passwordEncoder.matches(request.password(), user.getPassword());
        if (!match) {
            throw new BusinessException(CANNOT_LOGIN);
        }

        return jwtTokenService.generateAccessToken(user.getId(), user.getRole());
    }
}
