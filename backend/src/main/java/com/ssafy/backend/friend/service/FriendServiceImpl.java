package com.ssafy.backend.friend.service;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.friend.model.domain.Friend;
import com.ssafy.backend.friend.model.domain.UserId;
import com.ssafy.backend.friend.model.mapper.FriendMapper;
import com.ssafy.backend.friend.model.repository.FriendRepository;
import com.ssafy.backend.friend.model.vo.FriendVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.ssafy.backend.common.response.BaseResponseStatus.ALREADY_EXIST_FRIEND;

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
        if(!isFriend(PK))
            throw new BaseException(ALREADY_EXIST_FRIEND);

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

        List<FriendVO> friendListVOS = friendMapper.getFriendList(listUserId);

        log.info("---friendListVOS : {}", friendListVOS);

        return friendListVOS;


    }

    @Override
    public boolean isFriend(UserId PK) {
        Optional<Friend> friend = friendRepository.findById(PK);
//        log.info("친구 존재 여부 {}", friend);

        if(friend.isPresent()){
            return true;
        }
        return false;
    }



}
