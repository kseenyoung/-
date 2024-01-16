package com.ssafy.backend.friend.model.repository;

import com.ssafy.backend.friend.domain.Friend;
import com.ssafy.backend.friend.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, UserId> {



}
