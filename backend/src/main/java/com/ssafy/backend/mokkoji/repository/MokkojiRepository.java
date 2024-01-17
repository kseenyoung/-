package com.ssafy.backend.mokkoji.repository;

import com.ssafy.backend.mokkoji.domain.Mokkoji;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MokkojiRepository extends JpaRepository<Mokkoji, Integer> {

}
