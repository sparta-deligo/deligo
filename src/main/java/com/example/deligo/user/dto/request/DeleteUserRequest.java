package com.example.deligo.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserRequest {

    @NotBlank(message = "Password cannot be empty")
    private String password;

}
