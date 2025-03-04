package com.example.deligo.order.service.orderServ;

import com.example.deligo.menu.entity.Menu;
import com.example.deligo.order.dto.request.SaveReq;
import com.example.deligo.order.dto.response.SaveResp;
import com.example.deligo.store.entity.Store;
import com.example.deligo.user.entity.User;

import java.util.List;

public interface OrderService {
    SaveResp saveOrder(User user, Store store, List<Menu> menus, SaveReq saveReq);
}
