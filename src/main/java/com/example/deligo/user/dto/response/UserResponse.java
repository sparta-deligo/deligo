package com.example.deligo.user.dto.response;

import com.example.deligo.user.entity.User;
import com.example.deligo.user.entity.UserRole;
import lombok.Getter;

@Getter
public class UserResponse {
    private Long id;
    private String email;
    private UserRole role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
    }
}

