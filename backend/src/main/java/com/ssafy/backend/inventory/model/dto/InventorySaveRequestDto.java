package com.ssafy.backend.inventory.model.dto;
import java.util.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventorySaveRequestDto {
    private String userId;
    private List<IsWearingDto> isWearingDto;

}
