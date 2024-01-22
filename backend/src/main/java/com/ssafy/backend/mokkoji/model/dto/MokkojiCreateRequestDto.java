package com.ssafy.backend.mokkoji.model.dto;

import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MokkojiCreateRequestDto {
    private String mokkojiName;
    private String leaderId;
    private List<Integer> mokkojiCategories = new ArrayList<Integer>();

    public Mokkoji toEntity(){
        return Mokkoji.builder()
                .leaderId(leaderId)
                .mokkojiName(mokkojiName)
                .build();
    }
}
