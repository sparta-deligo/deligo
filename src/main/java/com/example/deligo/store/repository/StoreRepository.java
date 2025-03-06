package com.example.deligo.store.repository;

import com.example.deligo.store.dto.Response.StoreResponseDto;
import com.example.deligo.store.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    // User ID로 가게 개수 세기
    long countByUserId(Long userId);

    //N+1문제 해결을 위해 JOIN FETCH를 이용( 한 번에 DTO에서 가져오도록 해서 성능을 높임)
    @Query("SELECT new com.example.deligo.store.dto.Response.StoreResponseDto(s.id, o.id, s.name, s.category, s.openTime, s.closeTime, s.minOrderAmount, s.status, s.averageRating)" +
    "FROM Store s JOIN s.owner o WHERE  s.id = :id")
    Optional<StoreResponseDto> findByIdWithOwner(@Param("id") Long id);

    @Query("SELECT s FROM  Store s WHERE s.deletedAt IS NULL")
    Page<Store> findAllActiveStores(Pageable pageable); //삭제되지 않은 가게만 조회

    @Query("SELECT s FROM Store s WHERE  s.id = :id AND s.deletedAt IS NULL")
    Optional<Store> findActiveStoreById(@Param("id") Long id);
}
