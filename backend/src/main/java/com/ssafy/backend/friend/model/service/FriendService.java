package com.ssafy.backend.friend.model.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.friend.model.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface FriendService {

    public void requestFriend(String userId, String userId2) throws MyException;
}
