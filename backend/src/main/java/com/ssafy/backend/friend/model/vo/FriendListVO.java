package com.ssafy.backend.friend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FriendListVO {
    private int countFriend;
    private List<FriendVO> friends;
}
