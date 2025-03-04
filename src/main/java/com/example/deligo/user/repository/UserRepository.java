package com.example.deligo.user.repository;

import com.example.deligo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

<<<<<<< HEAD
@Repository
=======
>>>>>>> feat/signup
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
