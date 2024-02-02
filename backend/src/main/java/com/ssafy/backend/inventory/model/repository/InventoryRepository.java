package com.ssafy.backend.inventory.model.repository;

import com.ssafy.backend.inventory.model.domain.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findByUserIdAndInventoryId(String userId, int inventoryId);
    Page<Inventory> findAllByUserId(Pageable pageable, String userId);

    Page<Inventory> findAllByUserIdAndProduct_ProductCategory_ProductCategoryId(Pageable pageable, String userId, int productCategoryId);

    List<Inventory> findAllByUserId(String userId);

    @Query(value = "SELECT i.* FROM inventory i " +
            "JOIN product p ON i.product_id = p.product_id " +
            "WHERE i.user_id = :userId AND p.product_category_id " +
            "IN (SELECT p.product_category_id " +
            "FROM inventory i JOIN product p ON " +
            "i.product_id = p.product_id WHERE i.inventory_id " +
            "IN (:inventoryIds))", nativeQuery = true)
    List<Inventory> findAllByUserIdAndInventoryIdIn(String userId, List<Integer> inventoryIds);

    @Transactional
    void deleteByInventoryId(int inventoryId);
}
