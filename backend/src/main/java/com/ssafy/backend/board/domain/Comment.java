package com.ssafy.backend.board.domain;

import com.ssafy.backend.board.dto.CommentCreateRequestDto;
import com.ssafy.backend.common.domain.BaseTime;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentId;

    @ManyToOne
    @JoinColumn(name="boardId")
    private Board boardId;

    @Column
    private String userId;

    @Column(columnDefinition = "varchar (20)")
    private String comment;

    public long getCommentId() {
        return commentId;
    }
    public String getUserId() {
        return userId;
    }
    public String getComment() {
        return comment;
    }

    public Board getBoardId() {
        return boardId;
    }

    @Builder
    public Comment(Board boardId, String userId, String comment) {
        this.boardId = boardId;
        this.userId = userId;
        this.comment = comment;
    }

    public void modifyComment(String comment){
        this.comment = comment;
    }

}
