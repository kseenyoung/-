package com.ssafy.backend.mokkoji.model.vo;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.user.model.vo.UserInformationVO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MokkojiDetailVO {
    private Mokkoji mokkoji;
    private List<UserInformationVO> user;
    List<Category> categories;
    private boolean isLeader = false;
    private String userId;
    private int myMokkojiId;

    public void setMokkojiData(Mokkoji mokkoji, List<UserInformationVO> user, List<Category> categories) {
        this.mokkoji = mokkoji;
        this.user = user;
        this.categories = categories;
    }

    public void setMokkoji(Mokkoji mokkoji) {
        this.mokkoji = mokkoji;
    }

    public void setUser(List<UserInformationVO> user) {
        this.user = user;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setMyMokkojiId(int myMokkojiId) {
        this.myMokkojiId = myMokkojiId;
    }
}
