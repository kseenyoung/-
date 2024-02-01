package com.ssafy.backend.friend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FriendListVO {
    private int countFriend;
    private List<FriendVO> friends;

    public void setCountFriend(int countFriend) {
        this.countFriend = countFriend;
    }

    public void setFriends(List<FriendVO> friends) {
        this.friends = friends;
    }
}
