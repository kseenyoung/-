package com.ssafy.backend.friend.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.friend.model.domain.Friend;
import com.ssafy.backend.friend.model.domain.UserId;
import com.ssafy.backend.friend.model.mapper.FriendMapper;
import com.ssafy.backend.friend.model.vo.FriendVO;
import com.ssafy.backend.friend.model.repository.FriendRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class FriendServiceImpl implements FriendService {
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    FriendMapper friendMapper;


    @Transactional(rollbackOn = Exception.class)
    public void requestFriend(String userId, String userId2) {
        // 존재하는 userId인지 각각 확인 필요
        
        UserId PK = new UserId(userId, userId2);

        // 친구 등록 요청
        friendRepository.save(
                Friend.builder().
                        userId(PK).
                        isFriend(0).
                        build());
        // 알림 보내기

        }

    @Override
    public void accessFriend(String accessUserId, String accessUserId2) {
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
    public void quitFriend(String quitUserId, String quitUserId2) {
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
    public int countFriend(String countUserId) {
        return friendMapper.countFriend(countUserId);
    }

    @Override
    public List<FriendVO> listFriends(String listUserId) {

        List<FriendVO> friendListVOS = friendMapper.listFriends(listUserId);

        log.info("---friendListVOS : {}", friendListVOS);

        return friendListVOS;


    }

    public boolean isFriend(UserId PK) {
        Friend friend = friendRepository.findById(PK).orElseThrow(() -> new MyException("그런 정보 없음", HttpStatus.UNAUTHORIZED));
//        log.info("친구 존재 여부 {}", friend);

        if(friend.getIsFriend() == 0){
            return false;
        }
        return true;
    }




}
