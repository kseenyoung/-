package com.ssafy.backend.user.model.repository;

import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.user.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByUserNickname(String userTriedNickname);

    List<User> findAllByMokkojiId(Mokkoji mokkoji);

    Optional<User> findByMokkojiIdAndUserId(Mokkoji mokkoji, String userId);

}
