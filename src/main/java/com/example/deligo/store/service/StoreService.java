package com.example.deligo.store.service;

import com.example.deligo.store.dto.request.StoreSaveRequestDto;
import com.example.deligo.store.dto.request.StoreUpdateRequestDto;
import com.example.deligo.store.dto.response.StoreResponseDto;
import com.example.deligo.store.dto.response.StoreSaveResponseDto;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.repository.StoreRepository;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    //가게 생성
    @Transactional
    public StoreSaveResponseDto createStore(Long userId, StoreSaveRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 사용자입니다")
        );
        Store store = Store.builder()
                .user(user)
                .name(dto.getName())
                .category(dto.getStoreCategory())
                .openTime(dto.getOpenTime())
                .closeTime(dto.getCloseTime())
                .minOrderAmount(dto.getMinOrderAmount())
                .status(dto.getStatus())
                .build();

        Store savedStore = storeRepository.save(store);
        return new StoreSaveResponseDto(
                savedStore.getId(),
                savedStore.getOwner().getId(),
                savedStore.getName(),
               savedStore.getCategory(),
                savedStore.getOpenTime(),
                savedStore.getCloseTime(),
                savedStore.getMinOrderAmount(),
                savedStore.getStatus(),
                savedStore.getAverageRating()
        );
    }

    //모든 가게 조회
    @Transactional(readOnly = true)
    public List<StoreResponseDto> getAllStore() {
        List<Store> stores = storeRepository.findAll();
       List<StoreResponseDto> dtos = new ArrayList<>();
       for (Store store : stores) {
           dtos.add(new StoreResponseDto(
                   store.getId(),
                   store.getOwner().getId(),
                   store.getName(),
                   store.getCategory(),
                   store.getOpenTime(),
                   store.getCloseTime(),
                   store.getMinOrderAmount(),
                   store.getStatus(),
                   store.getAverageRating()));
       }
       return dtos;
    }

    //특정 가게 조회
    @Transactional(readOnly = true)
    public StoreResponseDto getByStoreID(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("가게 id를 찾을 수 없습니다")
        );
        return new StoreResponseDto(
                store.getId(),
                store.getOwner().getId(),
                store.getName(),
                store.getCategory(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinOrderAmount(),
                store.getStatus(),
                store.getAverageRating()
        );
    }

    //가게 수정
    @Transactional
    public StoreResponseDto updateStore(Long id, StoreUpdateRequestDto dto) {
        Store store = storeRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("가게 id를 찾을 수 없습니다")
        );
        store.Update(dto);
        return new StoreResponseDto(
                store.getId(),
                store.getOwner().getId(),
                store.getName(),
                store.getCategory(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinOrderAmount(),
                store.getStatus(),
                store.getAverageRating());
    }

    //가게 삭제
    @Transactional
    public void deleteByStoreId(Long id) {
        storeRepository.deleteById(id);
    }
}
