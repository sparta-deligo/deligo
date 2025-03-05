package com.example.deligo.order.repository;

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
    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Optional<Order> findWithId(@Param("id") Long id);
}
