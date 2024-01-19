package com.ssafy.backend.mokkoji.model.repository;

import com.ssafy.backend.mokkoji.model.domain.MokkojiCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MokkojiCategoryRepository extends JpaRepository<MokkojiCategory,Long> {
}
