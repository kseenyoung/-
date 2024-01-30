package com.ssafy.backend.category.model.repository;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {

    @Query("SELECT m FROM Mokkoji m " +
            "JOIN MokkojiCategory mc ON m.mokkojiId = mc.mokkoji.mokkojiId " +
            "WHERE mc.category.categoryId IN :categoryIds " +
            "AND m.mokkojiName like concat('%', :keyword, '%')")
    Page<Mokkoji> findByCategoryIds(@Param("categoryIds") List<Integer> categoryIds,
                                    @Param("keyword") String keyword,
                                    Pageable pageable);
}
