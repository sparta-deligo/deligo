package com.example.deligo.order.repository;

import com.example.deligo.menu.entity.Menu;
import com.example.deligo.order.entity.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(attributePaths = {"user", "store", "orderItems", "orderItems.menu"})
    Optional<Order> findWithId(Long id);

    @Query("select count(0) > 0 from Order o where o.menu.id = :menuId and " +
            "o.status not in ('DELIVERED','CANCELED')")
    Boolean existsActiveOrderByMenuId(@Param("menuId") Long menuId);
}
