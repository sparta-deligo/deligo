package com.example.deligo.store.service;

import com.example.deligo.common.dto.PaginationResponse;
import com.example.deligo.common.exception.CustomException;
import com.example.deligo.common.exception.ExceptionType;
import com.example.deligo.menu.dto.response.MenuResponse;
import com.example.deligo.menu.repository.MenuRepository;
import com.example.deligo.store.dto.Request.StoreSaveRequestDto;
import com.example.deligo.store.dto.Request.StoreUpdateRequestDto;
import com.example.deligo.store.dto.Response.StoreResponseDto;
import com.example.deligo.store.dto.Response.StoreSaveResponseDto;
import com.example.deligo.store.entity.Store;
import com.example.deligo.store.repository.StoreRepository;
import com.example.deligo.user.entity.User;
import com.example.deligo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    //가게 생성
    @Transactional
    public StoreSaveResponseDto createStore(Long userId, StoreSaveRequestDto dto) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new CustomException(ExceptionType.USER_NOT_FOUND)
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
                savedStore.getOwner().getId(), //n+1문제 fetch조인 이나 Entitygraph 사용해서 문제해결
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
    public PaginationResponse<StoreResponseDto> getAllStore(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Store> storePage = storeRepository.findAllActiveStores(pageable);


        Page<StoreResponseDto> dtoPage = storePage.map(store -> {
            // 메뉴 목록 가져오기
            List<MenuResponse> menuList = menuRepository.findAllByStore(store)
                    .stream()
                    .map(MenuResponse::from) // MenuResponse 변환 적용
                    .collect(Collectors.toList());

            return new StoreResponseDto(
                    store.getId(),
                    store.getOwner().getId(),
                    store.getName(),
                    store.getCategory(),
                    store.getOpenTime(),
                    store.getCloseTime(),
                    store.getMinOrderAmount(),
                    store.getStatus(),
                    store.getAverageRating(),
                    menuList // 메뉴 추가
            );
        });

        return new PaginationResponse<>(dtoPage);
    }

    @Transactional(readOnly = true)
    public StoreResponseDto getByStoreID(Long id) {
        Store store = storeRepository.findActiveStoreById(id)
                .orElseThrow(() -> new CustomException(ExceptionType.STORE_NOT_FOUND));

        // 메뉴 목록 가져오기
        List<MenuResponse> menuList = menuRepository.findAllByStore(store)
                .stream()
                .map(MenuResponse::from) // MenuResponse 변환 적용
                .collect(Collectors.toList());

        return new StoreResponseDto(
                store.getId(),
                store.getOwner().getId(),
                store.getName(),
                store.getCategory(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinOrderAmount(),
                store.getStatus(),
                store.getAverageRating(),
                menuList
        );
    }

    //가게 수정
    @Transactional
    public StoreResponseDto updateStore(Long id, StoreUpdateRequestDto dto, Long userId) {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionType.STORE_NOT_FOUND)
        );

        // 로그인한 사용자와 해당 가게의 소유자가 같은지 확인
        if (!store.getOwner().getId().equals(userId)) {
            throw new CustomException(ExceptionType.UNAUTHORIZED); // 접근 권한이 없는 경우
        }

        store.Update(dto);

        // 메뉴 목록 가져오기
        List<MenuResponse> menuList = menuRepository.findAllByStore(store)
                .stream()
                .map(MenuResponse::from)
                .collect(Collectors.toList());

        return new StoreResponseDto(
                store.getId(),
                store.getOwner().getId(),
                store.getName(),
                store.getCategory(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getMinOrderAmount(),
                store.getStatus(),
                store.getAverageRating(),
                menuList
                );
    }

    //가게 삭제
    @Transactional
    public void deleteByStoreId(Long id, Long userId) {
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new CustomException(ExceptionType.STORE_NOT_FOUND)
        );

        // 로그인한 사용자와 해당 가게의 소유자가 같은지 확인
        if (!store.getOwner().getId().equals(userId)) {
            throw new CustomException(ExceptionType.UNAUTHORIZED); // 접근 권한이 없는 경우
        }

        store.softDelete(); //하드 삭제 대신 softDelete()호출
    }

    @Transactional
    public void updateStoreAverageRating(Long storeId, Double averageRating) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ExceptionType.STORE_NOT_FOUND));
        store.updateAverageRating(averageRating);
    }
}