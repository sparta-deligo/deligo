package com.example.deligo.review.service;

import com.example.deligo.review.entity.OwnerComment;
import com.example.deligo.review.entity.Review;
import com.example.deligo.review.repository.OwnerCommentRepository;
import com.example.deligo.review.repository.ReviewRepository;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

class OwnerCommentServiceTest {

    @Mock
    private OwnerCommentRepository ownerCommentRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OwnerCommentService ownerCommentService;

    private User owner;
    private Review review;

    @Test
    void testCreateOwnerComment_Success() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(owner));
        when(reviewRepository.findById(1L)).thenReturn(java.util.Optional.of(review));

        ownerCommentService.createOwnerComment(1L, 1L, "Great review!");

        verify(ownerCommentRepository, times(1)).save(any(OwnerComment.class));
    }
}