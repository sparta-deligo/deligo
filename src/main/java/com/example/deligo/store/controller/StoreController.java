package com.example.deligo.store.controller;

import com.example.deligo.store.dto.request.StoreUpdateRequestDto;
import com.example.deligo.store.dto.response.StoreSaveResponseDto;
import com.example.deligo.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/stores")
    public ResponseEntity<StoreSaveResponseDto> save(@RequestBody StoreUpdateRequestDto dto) {
        return ResponseEntity.ok(storeService.save(dto));
    }

    @GetMapping("/stores")
    public ResponseEntity<List<StoreSaveResponseDto>> findAll() {
        return ResponseEntity.ok(storeService.findAll());
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity<StoreSaveResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.findById(id));
    }

    @PutMapping("/stores/{id}")
    public ResponseEntity<StoreSaveResponseDto> update(
            @PathVariable Long id,
            @RequestBody StoreUpdateRequestDto dto
    ) {
    return ResponseEntity.ok(storeService.update(id, dto));
    }

    @DeleteMapping("/stores/{id}")
    public void deleteById(Long id) {
        storeService.deleteById(id);
    }
}
