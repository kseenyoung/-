package com.ssafy.backend.category.model.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(columnDefinition = "varchar(10)")
    private String categoryName;



}
