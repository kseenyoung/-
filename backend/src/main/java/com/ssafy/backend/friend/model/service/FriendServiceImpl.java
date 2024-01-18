package com.ssafy.backend.friend.model.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.friend.domain.Friend;
import com.ssafy.backend.friend.domain.UserId;
import com.ssafy.backend.friend.model.mapper.FriendMapper;
import com.ssafy.backend.friend.model.repository.FriendRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class FriendServiceImpl implements FriendService {
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    FriendMapper friendMapper;


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
    public void accessFriend(String accessUserId, String accessUserId2) throws MyException {
        UserId PK = new UserId(accessUserId, accessUserId2);

        // 이미 친구인지 확인 필요

        friendRepository.save(
                Friend.builder()
                        .userId(PK)
                        .isFriend(1)
                        .build()
        );
    }

    @Override
    public void quitFriend(String quitUserId, String quitUserId2) throws MyException {
        UserId PK = new UserId(quitUserId, quitUserId2);

        // 이미 친구인지 확인
        if(!isFriend(PK)){
            throw new MyException("친구아님 ㅇㅇ", HttpStatus.BAD_REQUEST);
        }

        friendRepository.save(
                Friend.builder()
                        .userId(PK)
                        .isFriend(0)
                        .build());
    }

    @Override
    public int countFriend(String countUserId) throws MyException {
        return friendMapper.countFriend(countUserId);
    }

    public boolean isFriend(UserId PK) throws MyException{
        Friend friend = friendRepository.findById(PK).orElseThrow(() -> new MyException("그런 정보 없음", HttpStatus.UNAUTHORIZED));
//        log.info("친구 존재 여부 {}", friend);

        if(friend.getIsFriend() == 0){
            return false;
        }
        return true;
    }


}
