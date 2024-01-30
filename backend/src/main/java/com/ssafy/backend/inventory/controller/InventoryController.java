package com.ssafy.backend.inventory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.inventory.model.dto.InventoryResponseDto;
import com.ssafy.backend.inventory.model.dto.InventorySaveRequestDto;
import com.ssafy.backend.inventory.model.dto.IsWearingDto;
import com.ssafy.backend.inventory.service.InventoryService;
import com.ssafy.backend.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper;

    @PostMapping("")
    private BaseResponse<?> saveInventory(@RequestBody HashMap<String, Object> body,
                                          HttpServletRequest request) throws JsonProcessingException {
        HttpSession session = request.getSession(false);

        if(session == null) throw new BaseException(NOT_AUTH_MEMBER);
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();

        String sign = (String) body.get("sign");

        if ("save".equals(sign)) {
            List<Map<String, Object>> mapList  = (List<Map<String, Object>>) body.get("list");

            List<IsWearingDto> dtoList = mapList.stream()
                    .map(map -> objectMapper.convertValue(map, IsWearingDto.class))
                    .collect(Collectors.toList());
            inventoryService.saveInventory(InventorySaveRequestDto.builder()
                    .userId(userId)
                    .isWearingDto(dtoList)
                    .build());
            return new BaseResponse<>(SUCCESS);
        }
        throw new BaseException(EMPTY_SIGN);

    }

    @GetMapping("/get")
    private BaseResponse<?> getInventory(
            @RequestParam String userId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "category", defaultValue = "0") int category) {
        InventoryResponseDto inventory = inventoryService.getInventory(userId, page, category);

        return new BaseResponse<>(inventory);
    }

}
