package com.ssafy.backend.inventory.model.repository;

import com.ssafy.backend.inventory.model.domain.Inventory;
import com.ssafy.backend.product.model.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findByUserIdAndInventoryId(String userId, int inventoryId);

    @EntityGraph(attributePaths = {"product","product.productCategory"},type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT i FROM Inventory i left join i.product WHERE i.userId = :userId")
    Page<Inventory> findAllByUserId(Pageable pageable, String userId);

    Page<Inventory> findAllByUserIdAndProduct_ProductCategory_ProductCategoryId(Pageable pageable, String userId, int productCategoryId);

    List<Inventory> findAllByUserId(String userId);

    @EntityGraph(attributePaths = "product", type = EntityGraph.EntityGraphType.FETCH)
    @Query(value = "SELECT i FROM Inventory i " +
            "WHERE i.userId = :userId AND i.product.productCategory " +
            "IN (SELECT i.product.productCategory " +
            "FROM Inventory i WHERE i.inventoryId IN (:inventoryIds))")
    List<Inventory> findAllByUserIdAndInventoryIdIn(String userId, List<Integer> inventoryIds);

    @Transactional
    void deleteByInventoryId(int inventoryId);

    @Query("SELECT i.product " +
            "FROM Inventory i " +
            "WHERE i.product.productId = :productId " +
            "AND i.userId = :userId")
    Optional<Product> findProductByUserIdAndProductId(int productId,String userId);
}
