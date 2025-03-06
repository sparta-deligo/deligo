package com.example.deligo.menu.service;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.menu.dto.request.CreateMenuRequest;
import com.example.deligo.menu.dto.response.MenuResponse;
import com.example.deligo.menu.entity.Menu;
import com.example.deligo.menu.entity.MenuStatus;
import com.example.deligo.menu.repository.MenuRepository;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.entity.StoreCategory;
import com.example.deligo.store.entity.StoreStatus;
import com.example.deligo.store.repository.StoreRepository;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.entity.UserRole;
import com.example.deligo.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private StoreRepository storeRepository;
    @InjectMocks
    private MenuService menuService;

    @Test
    public void menu를_정상적으로_등록한다() {
        // Given
        Long userId = 1L;
        User user = new User("test@gmail.com", "password", "테스트", UserRole.OWNER);
        ReflectionTestUtils.setField(user, "id", userId);
        Long storeId = 1L;
        Store store = new Store(
                user,"스타벅스", StoreCategory.CAFE_DESSERT,
                LocalTime.parse("09:00:00"),
                LocalTime.parse("22:00:00"),
                10000, StoreStatus.OPEN
        );
        ReflectionTestUtils.setField(store, "id", storeId);

        CreateMenuRequest request = new CreateMenuRequest(
                storeId,
                "바닐라 크림 콜드브루",
                BigDecimal.valueOf(5800),
                "콜드 브루에 더해진 바닐라 크림으로 깔끔하면서 달콤한 콜드 브루를 새롭게 즐길 수 있는 음료입니다."
        );
        Long menuId = 1L;
        Menu menu = Menu.builder()
                .store(store)
                .name(request.getName())
                .price(request.getPrice())
                .description(request.getDescription())
                .status(MenuStatus.AVAILABLE)
                .build();
        ReflectionTestUtils.setField(menu, "id", menuId);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));
        given(menuRepository.save(any())).willReturn(menu);

        // When
        MenuResponse result = menuService.create(userId, request);

        // Then
        assertNotNull(result);
        assertEquals(storeId,result.getStoreId());
        assertEquals(MenuStatus.AVAILABLE, result.getStatus());
    }

    @Test
    public void menu_등록_중_사용자를_찾을_수_없어_에러가_발생한다() {
        // Given
        Long userId = 1L;
        Long storeId = 1L;
        CreateMenuRequest request = new CreateMenuRequest(
                storeId,
                "바닐라 크림 콜드브루",
                BigDecimal.valueOf(5800),
                "콜드 브루에 더해진 바닐라 크림으로 깔끔하면서 달콤한 콜드 브루를 새롭게 즐길 수 있는 음료입니다."
        );

        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // When & Then
        assertThrows(CustomException.class,
                () -> menuService.create(userId, request),
                ExceptionType.USER_NOT_FOUND.getMessage()
        );
    }

    @Test
    public void menu_등록_중_본인_가게가_아닌경우_에러가_발생한다() {
        // Given
        Long currentUserId = 1L;
        User currentUser = new User("test@gmail.com", "password", "테스트", UserRole.OWNER);
        ReflectionTestUtils.setField(currentUser, "id", currentUserId);

        Long otherUserId = 1L;
        User otherUser = new User("test2@gmail.com", "password", "테스트2", UserRole.OWNER);
        ReflectionTestUtils.setField(currentUser, "id", otherUserId);

        Long storeId = 1L;
        Store store = new Store(
                otherUser,"스타벅스", StoreCategory.CAFE_DESSERT,
                LocalTime.parse("09:00:00"),
                LocalTime.parse("22:00:00"),
                10000, StoreStatus.OPEN
        );

        given(userRepository.findById(anyLong())).willReturn(Optional.of(currentUser));
        given(storeRepository.findById(anyLong())).willReturn(Optional.of(store));


        CreateMenuRequest request = new CreateMenuRequest(
                storeId,
                "바닐라 크림 콜드브루",
                BigDecimal.valueOf(5800),
                "콜드 브루에 더해진 바닐라 크림으로 깔끔하면서 달콤한 콜드 브루를 새롭게 즐길 수 있는 음료입니다."
        );

        // When & Then
        assertThrows(CustomException.class,
                () -> menuService.create(currentUserId, request),
                ExceptionType.NO_PERMISSION_ACTION.getMessage()
        );
    }
}