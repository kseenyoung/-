package com.ssafy.backend.mokkoji.model.dto;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.user.model.vo.UserViewVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MokkojiDetailResponseDto {
    private Mokkoji mokkoji;
    private List<UserViewVO> user;
    List<Category> categories;
    private boolean isLeader = false;
    private String userId;
    private int myMokkojiId;

    public void setMokkojiData(Mokkoji mokkoji, List<UserViewVO> user, List<Category> categories) {
        this.mokkoji = mokkoji;
        this.user = user;
        this.categories = categories;
    }
}
