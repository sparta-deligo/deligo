package com.example.deligo.store.controller;

import com.example.deligo.common.annotation.UserId;
import com.example.deligo.common.dto.PaginationResponse;
import com.example.deligo.store.dto.Request.StoreSaveRequestDto;
import com.example.deligo.store.dto.Request.StoreUpdateRequestDto;
import com.example.deligo.store.dto.Response.StoreResponseDto;
import com.example.deligo.store.dto.Response.StoreSaveResponseDto;
import com.example.deligo.store.service.StoreService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    //가게 등록
    @PostMapping
    public ResponseEntity<StoreSaveResponseDto> createStore(
            @UserId Long userId,
           @Valid @RequestBody StoreSaveRequestDto dto,
            HttpServletRequest request // HttpServletRequest를 통해 userId를 받아옴
    ) {
         userId = (Long) request.getAttribute("userId"); // 필터에서 설정한 userId 가져오기
        StoreSaveResponseDto responseDto = storeService.createStore(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //모든 가게 조회
    @GetMapping
    public ResponseEntity<PaginationResponse<StoreResponseDto>> getAllStores(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        PaginationResponse<StoreResponseDto> stores = storeService.getAllStore(page, size);
        return ResponseEntity.ok(stores);
    }

    //특정 가게 조회
    @GetMapping("/{id}")
    public ResponseEntity<StoreResponseDto> getByStoreId(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getByStoreID(id));
    }

    //가게 수정
    @PutMapping("/{id}")
    public ResponseEntity<StoreResponseDto> updateStore(
            @PathVariable Long id,
            @RequestBody StoreUpdateRequestDto dto,
            HttpServletRequest request // HttpServletRequest를 통해 userId를 받아옴
    ) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(storeService.updateStore(id, dto, userId));
    }

    //가게 삭제
    @DeleteMapping("/{id}")
    public void deleteByStoreId(@PathVariable Long id, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        storeService.deleteByStoreId(id, userId);
    }
}