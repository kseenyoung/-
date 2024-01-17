package com.ssafy.backend.mokkoji.repository;

import com.ssafy.backend.mokkoji.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
