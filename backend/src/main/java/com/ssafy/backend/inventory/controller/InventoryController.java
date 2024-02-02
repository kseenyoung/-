package com.ssafy.backend.inventory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.inventory.model.vo.InventoryPageVO;
import com.ssafy.backend.inventory.model.dto.InventorySaveRequestDTO;
import com.ssafy.backend.inventory.service.InventoryService;
import com.ssafy.backend.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;
    @PostMapping("")
    private BaseResponse<?> iventoryHideURL(
            @RequestBody HashMap<String, Object> body,
                                          HttpServletRequest request) throws JsonProcessingException {
        HttpSession session = request.getSession(false);

        if(session == null) throw new BaseException(NOT_AUTH_MEMBER);
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();

        String sign = (String) body.get("sign");
        switch(sign){
            case "equip":
                List<Integer> itemList  = (List<Integer>) body.get("itemList");

                inventoryService.equipItem(InventorySaveRequestDTO.builder()
                        .userId(userId)
                        .itemList(itemList)
                        .build());
                return new BaseResponse<>(SUCCESS);
            case "unEquip":
                int unEquipItem = (int) body.get("unEquipItem");
                inventoryService.unEquipItem(unEquipItem,userId);
                return new BaseResponse<>(SUCCESS);
        }
        throw new BaseException(NOT_MATCH_SIGN);

    }

    @GetMapping("/get")
    private BaseResponse<?> getInventory(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "category", defaultValue = "0") int category) {

        HttpSession session = request.getSession(false);
        if(session == null) throw new BaseException(NOT_AUTH_MEMBER);
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();

        InventoryPageVO inventory = inventoryService.getInventory(userId, page, category);

        return new BaseResponse<>(inventory);
    }







}
