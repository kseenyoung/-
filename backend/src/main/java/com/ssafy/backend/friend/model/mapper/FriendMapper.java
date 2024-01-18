package com.ssafy.backend.friend.model.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FriendMapper {

    public int countFriend(String countUserId);
}
