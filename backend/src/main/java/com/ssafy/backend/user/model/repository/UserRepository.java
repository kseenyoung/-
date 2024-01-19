package com.ssafy.backend.user.model.repository;

import com.ssafy.backend.user.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByUserNickname(String userTriedNickname);
}
