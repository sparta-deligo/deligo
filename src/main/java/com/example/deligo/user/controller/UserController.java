package com.example.deligo.user.controller;

import com.example.deligo.common.dto.ApiResponse;
import com.example.deligo.common.annotation.UserId;
import com.example.deligo.user.dto.request.LoginRequest;
import com.example.deligo.user.dto.request.SignupRequest;
import com.example.deligo.user.dto.request.DeleteUserRequest;
import com.example.deligo.user.dto.request.UpdateUserRequest;
import com.example.deligo.user.dto.response.UserResponse;
import com.example.deligo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupRequest request) {
        UserResponse response = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {
        Map<String, String> tokenInfo = userService.login(request);
        return ResponseEntity.ok(tokenInfo);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(
            @UserId Long userId,
            @Valid @RequestBody DeleteUserRequest request
    ) {
        String password = request.getPassword();
        userService.deleteUser(userId, password);
        return ResponseEntity.ok(new ApiResponse("회원탈퇴 완료"));
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUser(@UserId Long userId) {
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(
            @UserId Long userId,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        UserResponse response = userService.updateUser(userId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(
            @RequestHeader("Authorization") String token
    ) {
        userService.logout(token);
        return ResponseEntity.ok(new ApiResponse("로그아웃 완료"));
    }
}
