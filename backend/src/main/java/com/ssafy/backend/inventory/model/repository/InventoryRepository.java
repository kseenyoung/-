package com.ssafy.backend.inventory.model.repository;

import com.ssafy.backend.inventory.model.domain.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Page<Inventory> findAllByUserId(Pageable pageable, String userId);

    Page<Inventory> findAllByUserIdAndProduct_ProductCategory_ProductCategoryId(Pageable pageable, String userId, int productCategoryId);

    List<Inventory> findAllByUserId(String userId);
}
