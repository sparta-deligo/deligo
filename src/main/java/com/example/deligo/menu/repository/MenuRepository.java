package com.example.deligo.menu.repository;

import com.example.deligo.menu.entity.Menu;
import com.example.deligo.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select m from Menu m where m.store = :store and m.status != 'DELETED'")
    List<Menu> findAllByStore(@Param("store") Store store);
}
