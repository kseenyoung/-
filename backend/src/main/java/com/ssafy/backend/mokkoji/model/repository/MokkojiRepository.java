package com.ssafy.backend.mokkoji.model.repository;

import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MokkojiRepository extends JpaRepository<Mokkoji, Integer> {

}
