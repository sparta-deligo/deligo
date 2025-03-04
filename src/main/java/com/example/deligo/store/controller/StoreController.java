package com.example.deligo.store.controller;

import com.example.deligo.store.dto.request.StoreSaveRequestDto;
import com.example.deligo.store.dto.request.StoreUpdateRequestDto;
import com.example.deligo.store.dto.response.StoreResponseDto;
import com.example.deligo.store.dto.response.StoreSaveResponseDto;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.service.StoreService;
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
            @RequestParam Long userId,
            @RequestBody StoreSaveRequestDto dto
    ) {
        StoreSaveResponseDto responseDto = storeService.createStore(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //모든 가게 조회
   @GetMapping
    public ResponseEntity<List<StoreResponseDto>> getAllStore() {
        List<StoreResponseDto> store = storeService.getAllStore();
        return ResponseEntity.ok(store);
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
            @RequestBody StoreUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(storeService.updateStore(id, dto));
    }

    //가게 삭제
    @DeleteMapping("/{id}")
    public void deleteByStoreId(Long id) {
        storeService.deleteByStoreId(id);
    }
}