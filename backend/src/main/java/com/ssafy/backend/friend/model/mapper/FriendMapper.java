package com.ssafy.backend.friend.model.mapper;

import com.ssafy.backend.friend.model.FriendVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendMapper {

    public int countFriend(String countUserId);

    List<FriendVO> listFriends(String listUserId);
}
