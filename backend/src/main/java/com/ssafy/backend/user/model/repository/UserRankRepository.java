package com.ssafy.backend.user.model.repository;

import com.ssafy.backend.user.model.domain.UserRank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRankRepository extends JpaRepository<UserRank, String> {
    UserRank findUserRankByUserId(String userId);

    @Query("SELECT u FROM UserRank u WHERE u.userTotalStudyTime IS NOT NULL")
    Page<UserRank> findTop10ByUserRankTotalTimeIsNotNull(Pageable pageable);
}
