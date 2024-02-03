package com.ssafy.backend.product.model.repository;

import com.ssafy.backend.product.model.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByProductCategory_ProductCategoryId(int productCategoryId);

    @EntityGraph(attributePaths = {"productCategory"} ,type= EntityGraph.EntityGraphType.FETCH)
    Page<Product> findAll(Pageable pageable);
}
