package com.example.deligo.user.controller;

import com.example.deligo.common.dto.ApiResponse;
import com.example.deligo.user.dto.request.LoginRequest;
import com.example.deligo.user.dto.request.SignupRequest;
import com.example.deligo.user.dto.response.UserResponse;
import com.example.deligo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody SignupRequest request) {
        UserResponse response = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {
        userService.login(request);
        return ResponseEntity.ok(new ApiResponse("로그인 성공!"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(
            @PathVariable Long userId,
            @RequestParam String password
    ) {
        userService.deleteUser(userId, password);
        return ResponseEntity.ok(new ApiResponse("회원탈퇴 완료"));
    }
}

