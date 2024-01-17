package com.ssafy.backend.mokkoji.repository;

import com.ssafy.backend.mokkoji.domain.MokkojiCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MokkojiCategoryRepository extends JpaRepository<MokkojiCategory,Long> {
}
