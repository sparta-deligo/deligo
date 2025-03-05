package com.example.deligo.user.service;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.common.jwt.JwtUtil;
import com.example.deligo.config.PasswordEncoder;
import com.example.deligo.user.dto.request.LoginRequest;
import com.example.deligo.user.dto.request.SignupRequest;
import com.example.deligo.user.dto.response.UserResponse;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResponse signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(ExceptionType.DUPLICATE_EMAIL);
        }

        if (userRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new CustomException(ExceptionType.DUPLICATE_NICKNAME);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), encodedPassword, request.getNickname(), request.getRole());
        userRepository.save(user);
        return new UserResponse(user);
    }

    @Transactional
    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ExceptionType.INVALID_CREDENTIALS));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ExceptionType.INVALID_CREDENTIALS);
        }

        return jwtUtil.generateToken(user.getId()).get("token");
    }

    @Transactional
    public void deleteUser(Long userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ExceptionType.INVALID_CREDENTIALS);
        }
        userRepository.delete(user);
    }
}




