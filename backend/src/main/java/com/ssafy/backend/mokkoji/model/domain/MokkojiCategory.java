package com.ssafy.backend.mokkoji.model.domain;

import com.ssafy.backend.category.model.domain.Category;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class MokkojiCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long MokkojiCategoryId;

    @ManyToOne
    @JoinColumn(name = "MokkojiId")
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
