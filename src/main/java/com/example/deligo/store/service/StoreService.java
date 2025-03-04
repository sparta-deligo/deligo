package com.example.deligo.store.service;

import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.store.dto.Request.StoreSaveRequestDto;
import com.example.deligo.store.dto.Request.StoreUpdateRequestDto;
import com.example.deligo.store.dto.Response.StoreResponseDto;
import com.example.deligo.store.dto.Response.StoreSaveResponseDto;
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
                .user(user) // userId로 사용자 객체 가져옴
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

    @Transactional(readOnly = true)
    public List<StoreResponseDto> getAllStore() {
        List<Store> stores = storeRepository.findAll();
        List<StoreResponseDto> dtos = new ArrayList<>();
        for (Store store : stores) {
            dtos.add(new StoreResponseDto( store.getId(),
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

    @Transactional(readOnly = true)
    public StoreResponseDto getByStoreID(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("가게 id를 찾을 수 없습니다")
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
    public StoreResponseDto updateStore(Long id, StoreUpdateRequestDto dto, Long userId) {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("가게 id를 찾을 수 없습니다")
        );

        // 로그인한 사용자와 해당 가게의 소유자가 같은지 확인
        if (!store.getOwner().getId().equals(userId)) {
            throw new CustomException(ExceptionType.UNAUTHORIZED); // 접근 권한이 없는 경우
        }

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
    public void deleteByStoreId(Long id, Long userId) {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("가게 id를 찾을 수 없습니다")
        );

        // 로그인한 사용자와 해당 가게의 소유자가 같은지 확인
        if (!store.getOwner().getId().equals(userId)) {
            throw new CustomException(ExceptionType.UNAUTHORIZED); // 접근 권한이 없는 경우
        }

        storeRepository.deleteById(id);
    }
}

