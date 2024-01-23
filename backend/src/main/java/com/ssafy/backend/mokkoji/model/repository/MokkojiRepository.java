package com.ssafy.backend.mokkoji.model.repository;

import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MokkojiRepository extends JpaRepository<Mokkoji, Integer> {
    Optional<Mokkoji> findByMokkojiName(String mokkojiName);
    Page<Mokkoji> findByMokkojiNameContaining(String mokkojiName, Pageable pageable);

    Optional<Mokkoji> findMokkojiByMokkojiId(int mokkojiId);
}
