package com.ssafy.backend.friend.model.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.friend.model.FriendVO;
import com.ssafy.backend.friend.model.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FriendService {

    public void requestFriend(String userId, String userId2);

    public void accessFriend(String accessUserId, String accessUuserId2);

    public void quitFriend(String quitUserId, String quitUserId2);

    int countFriend(String countUserId);

    List<FriendVO> listFriends(String listUserId);
}