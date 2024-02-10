package com.ssafy.backend.mokkoji.model.repository;

import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.mokkoji.model.domain.MokkojiCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MokkojiCategoryRepository extends JpaRepository<MokkojiCategory,Long> {
    @EntityGraph(attributePaths = {"mokkoji", "category"})
    List<MokkojiCategory> findByMokkojiIn(List<Mokkoji> mokkoji);

    void deleteAllByMokkoji(Mokkoji mokkoji);

    List<MokkojiCategory> findByMokkoji(Mokkoji mokkoji);
}
