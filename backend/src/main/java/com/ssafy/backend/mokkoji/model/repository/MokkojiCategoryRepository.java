package com.ssafy.backend.mokkoji.model.repository;

import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.mokkoji.model.domain.MokkojiCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MokkojiCategoryRepository extends JpaRepository<MokkojiCategory,Long> {
    List<MokkojiCategory> findByMokkojiIn(List<Mokkoji> mokkoji);

    void deleteAllByMokkoji(Mokkoji mokkoji);
}
