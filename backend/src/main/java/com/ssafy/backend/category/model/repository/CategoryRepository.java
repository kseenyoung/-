package com.ssafy.backend.category.model.repository;

import com.ssafy.backend.category.model.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
