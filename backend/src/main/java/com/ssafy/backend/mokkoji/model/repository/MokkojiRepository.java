package com.ssafy.backend.mokkoji.model.repository;

import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MokkojiRepository extends JpaRepository<Mokkoji, Integer> {
    Optional<Mokkoji> findByMokkojiName(String mokkojiName);
}
