package com.example.deligo.review.repository;

import com.example.deligo.review.entity.OwnerComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerCommentRepository extends JpaRepository<OwnerComment, Long> {
}
