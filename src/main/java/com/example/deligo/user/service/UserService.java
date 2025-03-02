package com.example.deligo.user.service;

import com.example.deligo.user.entity.User;
import com.example.deligo.user.repository.UserRepository;
import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ExceptionType.USER_NOT_FOUND));
    }
}