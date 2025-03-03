package com.example.deligo.menu.controller;

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
public class MenuController {  // TODO: 회원가입 & 로그인 로직 구현 시 userId 로직 변경 필요

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(
            @RequestParam Long userId,
            @Valid @RequestBody CreateMenuRequest request
    ) {
        return new ResponseEntity<>(menuService.create(userId, request), HttpStatus.CREATED);
    }


}
