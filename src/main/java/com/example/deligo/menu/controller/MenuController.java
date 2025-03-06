package com.example.deligo.menu.controller;

import com.example.deligo.common.annotation.UserId;
import com.example.deligo.common.dto.ApiResponse;
import com.example.deligo.menu.dto.request.CreateMenuRequest;
import com.example.deligo.menu.dto.request.UpdateMenuRequest;
import com.example.deligo.menu.dto.response.MenuResponse;
import com.example.deligo.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(
            @UserId Long userId,
            @Valid @RequestBody CreateMenuRequest request
    ) {
        return new ResponseEntity<>(menuService.create(userId, request), HttpStatus.CREATED);
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(
            @UserId Long userId,
            @PathVariable Long menuId,
            @Valid @RequestBody UpdateMenuRequest request
    ) {
        return new ResponseEntity<>(menuService.update(userId, menuId, request), HttpStatus.OK);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<ApiResponse> deleteMenu(
            @UserId Long userId,
            @PathVariable Long menuId
    ) {
        menuService.delete(userId, menuId);
        return new ResponseEntity<>(new ApiResponse("메뉴가 삭제 처리되었습니다."),HttpStatus.OK);
    }
}
