package com.ssafy.backend.user.model.repository;

import com.ssafy.backend.user.model.domain.UserRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRankRepository extends JpaRepository<UserRank, String> {
    UserRank findUserRankByUserId(String userId);


}
