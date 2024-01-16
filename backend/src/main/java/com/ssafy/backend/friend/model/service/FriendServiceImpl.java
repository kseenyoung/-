package com.ssafy.backend.friend.model.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.friend.domain.Friend;
import com.ssafy.backend.friend.domain.UserId;
import com.ssafy.backend.friend.model.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    FriendRepository friendRepository;

    public void requestFriend(String userId, String userId2) throws MyException {
        // 존재하는 userId인지 각각 확인 필요
        
        UserId PK = new UserId(userId, userId2);

        friendRepository.save(
                Friend.builder().
                        userId(PK).
                        isFriend(0).
                        build());
    }

    @Override
    public void accessFriend(String accessUserId, String accessUuserId2) throws MyException {
        UserId PK = new UserId(accessUuserId2, accessUserId);

        friendRepository.save(
                Friend.builder()
                        .userId(PK)
                        .isFriend(1)
                        .build()
        );
    }

}
