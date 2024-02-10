package com.ssafy.backend.mokkoji.model.domain;

import com.ssafy.backend.category.model.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class MokkojiCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long MokkojiCategoryId;

    @ManyToOne
    @JoinColumn(name = "mokkojiId")
    private Mokkoji mokkoji;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Builder
    public MokkojiCategory(Mokkoji mokkoji, Category category) {
        this.mokkoji = mokkoji;
        this.category = category;
    }
}
