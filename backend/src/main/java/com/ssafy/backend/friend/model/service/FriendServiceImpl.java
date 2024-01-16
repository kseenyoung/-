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
        UserId PK = new UserId(userId, userId2);

        friendRepository.save(
                Friend.builder().
                        userId(PK).
                        isFriend(0).
                        build());
    }

}
