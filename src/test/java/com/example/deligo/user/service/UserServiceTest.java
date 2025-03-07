package com.example.deligo.user.service;

import com.example.deligo.user.entity.User;
import com.example.deligo.user.entity.UserRole;
import com.example.deligo.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공 테스트")
    void signup_success() throws Exception {
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "test@example.com",
                                    "password": "Password123!",
                                    "nickname": "TestUser",
                                    "role": "USER"
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("중복 이메일 회원가입 실패 테스트")
    void signup_duplicate_email_fail() throws Exception {
        userRepository.save(new User("duplicate@example.com", "EncodedPassword123!", "UserTest", UserRole.USER));

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "duplicate@example.com",
                                    "password": "Password123!",
                                    "nickname": "UserTest",
                                    "role": "USER"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("중복 닉네임 회원가입 실패 테스트")
    void signup_duplicate_nickname_fail() throws Exception {
        userRepository.save(new User("newuser@example.com", "EncodedPassword123!", "duplicateNick", UserRole.USER));

        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "newuser2@example.com",
                                    "password": "Password123!",
                                    "nickname": "duplicateNick",
                                    "role": "USER"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("비밀번호 유효성 검사 실패 테스트")
    void signup_invalid_password_fail() throws Exception {
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "test@example.com",
                                    "password": "1234",
                                    "nickname": "ValidNick",
                                    "role": "USER"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("잘못된 이메일 형식 회원가입 실패 테스트")
    void signup_invalid_email_fail() throws Exception {
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "invalid-email",
                                    "password": "Password123!",
                                    "nickname": "ValidNick",
                                    "role": "USER"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("닉네임 길이 제한 실패 테스트")
    void signup_invalid_nickname_length_fail() throws Exception {
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "test@example.com",
                                    "password": "Password123!",
                                    "nickname": "A",
                                    "role": "USER"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}


