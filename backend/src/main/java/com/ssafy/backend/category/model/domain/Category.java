package com.ssafy.backend.category.model.domain;

import com.ssafy.backend.common.model.domain.BaseTime;
import com.ssafy.backend.mokkoji.model.domain.MokkojiCategory;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(columnDefinition = "varchar(10)")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<MokkojiCategory> mokkojiCategories = new ArrayList<>();


}
