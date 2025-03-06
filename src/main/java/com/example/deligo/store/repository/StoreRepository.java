package com.example.deligo.store.repository;

import com.example.deligo.store.entity.Store;
import com.example.deligo.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Page<Store> findByNameContaining(String name, Pageable pageable);

    @Query("SELECT s FROM  Store s WHERE s.deletedAt IS NULL")
    Page<Store> findAllActiveStores(Pageable pageable); //삭제되지 않은 가게만 조회

    @Query("SELECT s FROM Store s WHERE  s.id = :id AND s.deletedAt IS NULL")
    Optional<Store> findActiveStoreById(@Param("id") Long id);

    long countByOwner(User owner);
}
