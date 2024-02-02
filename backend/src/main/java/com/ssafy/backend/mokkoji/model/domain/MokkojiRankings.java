package com.ssafy.backend.mokkoji.model.domain;

import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Immutable
@Getter
@ToString
public class MokkojiRankings {
    @Id
    private int mokkojiId;

    @Column
    private String mokkojiName;

    @Column
    private String leaderId;

    @Column
    private int totalMemoryTime;

    @Column(name = "categories")
    private String categoryIds;  // '1,2,3' 형태로 저장된 문자열

    @Column
    private int rank;

    @Transient  // DB에 매핑되지 않는 필드
    private List<Integer> categories = new ArrayList<Integer>();


    @PostLoad
        // 엔터티를 DB로부터 로드한 후에 실행되는 메서드
    void fillCategories() {
        if(categoryIds == null) this.categories = new ArrayList<Integer>();
        else {
            this.categories = Arrays.stream(categoryIds.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        }
    }

}
