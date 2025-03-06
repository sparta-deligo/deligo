package com.example.deligo.review.service;
import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.order.entity.Order;
import com.example.deligo.review.entity.OwnerComment;
import com.example.deligo.review.entity.Review;
import com.example.deligo.review.repository.OwnerCommentRepository;
import com.example.deligo.review.repository.ReviewRepository;
import com.example.deligo.store.entity.Store;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.repository.UserRepository;
import com.example.deligo.user.entity.UserRole;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private OwnerComment ownerComment;
    private Order order;

    @BeforeEach
    void setUp() {
        org.mockito.MockitoAnnotations.openMocks(this);

        owner = new User("test@example.com", "password", "Test User", UserRole.OWNER);

        Store store = mock(Store.class);
        when(store.getOwnerId()).thenReturn(owner.getId());

        Order order = mock(Order.class);
        when(order.getStore()).thenReturn(store);
        review = new Review(owner, order, 5, "Great service!", store);

        ownerComment = new OwnerComment(owner, review, "Great comment!");
    }

    @Test
    void testCreateOwnerComment_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        ownerCommentService.createOwnerComment(1L, 1L, "Great review!");

        verify(ownerCommentRepository, times(1)).save(any(OwnerComment.class));
    }

    @Test
    void testUpdateOwnerComment_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(ownerCommentRepository.findById(1L)).thenReturn(Optional.of(ownerComment));

        ownerCommentService.updateOwnerComment(1L, 1L, "Updated review");

        verify(ownerCommentRepository, times(1)).save(any(OwnerComment.class));
    }

    @Test
    void testUpdateOwnerComment_Fail_NotOwner() {
        User nonOwner = new User("nonowner@example.com", "password", "Non Owner", UserRole.USER);
        when(userRepository.findById(2L)).thenReturn(Optional.of(nonOwner));
        when(ownerCommentRepository.findById(1L)).thenReturn(Optional.of(ownerComment));

        CustomException thrown = assertThrows(CustomException.class, () -> {
            ownerCommentService.updateOwnerComment(2L, 1L, "Invalid update");
        });

        assertEquals(ExceptionType.NO_PERMISSION_ACTION, thrown.getExceptionType());
    }

    @Test
    void testDeleteOwnerComment_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(ownerCommentRepository.findById(1L)).thenReturn(Optional.of(ownerComment));

        ownerCommentService.deleteOwnerComment(1L, 1L);

        verify(ownerCommentRepository, times(1)).delete(any(OwnerComment.class));
    }

    @Test
    void testDeleteOwnerComment_Fail_NotOwner() {
        User nonOwner = new User("nonowner@example.com", "password", "Non Owner", UserRole.USER);
        when(userRepository.findById(2L)).thenReturn(Optional.of(nonOwner));
        when(ownerCommentRepository.findById(1L)).thenReturn(Optional.of(ownerComment));

        CustomException thrown = assertThrows(CustomException.class, () -> {
            ownerCommentService.deleteOwnerComment(2L, 1L);
        });

        assertEquals(ExceptionType.NO_PERMISSION_ACTION, thrown.getExceptionType());
    }
}