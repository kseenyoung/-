package com.ssafy.backend.mokkoji.model.domain;

import com.ssafy.backend.mokkoji.model.dto.CategoryDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(columnDefinition = "varchar(10)")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<MokkojiCategory> mokkojiCategories = new ArrayList<>();


}
