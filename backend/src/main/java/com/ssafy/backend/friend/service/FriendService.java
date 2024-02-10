package com.ssafy.backend.friend.service;

import com.ssafy.backend.friend.model.domain.UserId;
import com.ssafy.backend.friend.model.vo.FriendVO;
import com.ssafy.backend.user.model.domain.redis.LoginRedis;
import com.ssafy.backend.user.model.vo.LoginRedisVO;

import java.util.List;


public interface FriendService {

    public void requestFriend(String userId, String userId2);

    public void accessFriend(String accessUserId, String accessUuserId2);

    public void quitFriend(String quitUserId, String quitUserId2);

    int countFriend(String countUserId);

    List<FriendVO> listFriends(String listUserId);

    boolean isFriend(UserId PK);

    List<LoginRedisVO> getLoginFriends(String userId);
}