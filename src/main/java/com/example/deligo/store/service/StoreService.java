package com.example.deligo.store.service;

import com.example.deligo.store.dto.request.StoreUpdateRequestDto;
import com.example.deligo.store.dto.response.StoreSaveResponseDto;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public StoreSaveResponseDto save(StoreUpdateRequestDto dto) {
        Store store = new Store(dto.getName());
        Store savestore = storeRepository.save(store);
        return new StoreSaveResponseDto(
                savestore.getId(), savestore.getName(), savestore.getOpenTime(), savestore.getCloseTime(), savestore.getMinOrderAmount(), savestore.getCategory(), savestore.getAverage_rating());
    }

    @Transactional(readOnly = true)
    public List<StoreSaveResponseDto> findAll() {
        List<Store> stores = storeRepository.findAll();
        List<StoreSaveResponseDto> dtos = new ArrayList<>();
        for(Store store : stores) {
            dtos.add(new StoreSaveResponseDto(store.getId(), store.getName(), store.getOpenTime(), store.getCloseTime(), store.getMinOrderAmount(), store.getCategory(), store.getAverage_rating()));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public StoreSaveResponseDto findById(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다")
        );
        return new StoreSaveResponseDto(store.getId(), store.getName(), store.getOpenTime(), store.getCloseTime(), store.getMinOrderAmount(), store.getCategory(), store.getAverage_rating());
    }

    @Transactional
    public StoreSaveResponseDto update(Long id, StoreUpdateRequestDto dto) {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다")
        );
        store.update(
                dto.getName(),
                dto.getOpenTime(),
                dto.getCloseTime(),
                dto.getMinOrderAmount(),
                dto.getCategory(),
                dto.getStatus()
        );
        return new StoreSaveResponseDto(store.getId(), store.getName(),store.getOpenTime(), store.getCloseTime(), store.getMinOrderAmount(), store.getCategory(), store.getAverage_rating());
    }

    @Transactional
    public void deleteById(Long id) {
        storeRepository.deleteById(id);
    }
}
