package com.example.deligo.order.service.orderServ;

import com.example.deligo.menu.entity.Menu;
import com.example.deligo.menu.entity.MenuStatus;
import com.example.deligo.menu.repository.MenuRepository;
import com.example.deligo.order.dto.request.SaveRequest;
import com.example.deligo.order.dto.response.SaveResponse;
import com.example.deligo.order.repository.OrderItemRepository;
import com.example.deligo.order.repository.OrderRepository;
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

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepo;
    @Mock
    private OrderItemRepository orderItemRepo;
    @Mock
    private UserRepository userServ;
    @Mock
    private StoreRepository storeServ;
    @Mock
    private MenuRepository menuServ;

    @Test
    void 주문_정상_작동_확인() {
        /*given*/
        Long userId = 1L;
        Long storeId = 1L;
        int quantity = 4;
        List<Long> menuIds = List.of(1L, 2L);
        String deliverAddress = "~~시 ~~구 ~~동 ...";
        String storeComment = "리뷰이벤트할게요 새우튀김으로 부탁드려요 :)";
        String riderComment = "천천히 와주세요";

        User user = new User("testEmail", "testPw", "testName", UserRole.USER);
        Store store = new Store(user, "testStore", StoreCategory.CHICKEN, LocalTime.now(), LocalTime.now(), 5, StoreStatus.OPEN);
        SaveRequest saveRequest = new SaveRequest(deliverAddress, menuIds, quantity, riderComment, storeComment, storeId);
        List<Menu> menus = new ArrayList<>();
        menus.add(new Menu(store, "testMenu1", "desc", new BigDecimal("10.5"), MenuStatus.AVAILABLE));
        menus.add(new Menu(store, "testMenu2", "desc", new BigDecimal("10.5"), MenuStatus.AVAILABLE));

        /*when*/
        SaveResponse saveResponse = orderService.saveOrder(user, store, menus, saveRequest);

        /*then*/
        assertNotNull(saveResponse);
    }
}