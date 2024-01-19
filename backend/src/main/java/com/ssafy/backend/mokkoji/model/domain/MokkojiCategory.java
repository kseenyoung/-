package com.ssafy.backend.mokkoji.model.domain;

import javax.persistence.*;

@Entity
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

}
