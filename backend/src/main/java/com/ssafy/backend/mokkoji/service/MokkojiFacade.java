package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.category.model.domain.Category;
import com.ssafy.backend.category.model.dto.CategoryDto;
import com.ssafy.backend.category.service.CategoryService;
import com.ssafy.backend.mokkoji.model.domain.Mokkoji;
import com.ssafy.backend.mokkoji.model.domain.MokkojiRankings;
import com.ssafy.backend.mokkoji.model.dto.*;
import com.ssafy.backend.user.model.domain.User;
import com.ssafy.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//각 서비스를 끌어다 쓰기 위해서 퍼사드 형태로 모듈 분리
//하나의 서비스에는 하나의 레포만 존재하도록 설정 -> 비지니스 로직 처리
@RequiredArgsConstructor
@Service
@Slf4j
public class MokkojiFacade {
    private final int CREATE_MOKKOJI_POINT = 5000;
    private final CategoryService categoryService;
    private final MokkojiRankingService mokkojiRankingService;
    private final MokkojiService mokkojiService;
    private final MokkojiCategoryService mokkojiCategoryService;
    private final UserService userService;

    @Transactional(rollbackFor = Exception.class)
    public void saveMokkoji(MokkojiCreateRequestDto dto){
        User user = userService.canCreateMokkoji(dto.getLeaderId(), CREATE_MOKKOJI_POINT);
        Mokkoji mokkoji = mokkojiService.createMokkoji(dto.toEntity());
        List<Category> categories = categoryService.getCategories(dto.getMokkojiCategories());
        for (Category category : categories) {
            mokkojiCategoryService.createMokkjiCategory(mokkoji, category);
        }
        userService.saveMokkojiId(user, mokkoji);
    }


    public MokkojiRankingsResponseDto getByMokkojiNameRanking(String mokkojiName) {
        List<MokkojiRankings> byMokkojiName = mokkojiRankingService.getByMokkojiName(mokkojiName);
        log.info("모꼬지 이름 랭킹 검색입니다.{}",byMokkojiName);
        MokkojiRankings mokkojiRankings = byMokkojiName.get(0);

        MokkojiRankDto mokkojiDto = new MokkojiRankDto(mokkojiRankings);
        List<Category> categoriesEntities = categoryService.getCategories(mokkojiRankings.getCategories());

        List<CategoryDto> categories = categoriesEntities
                .stream().map(CategoryDto::new)
                .collect(Collectors.toList());
        return new MokkojiRankingsResponseDto(categories, mokkojiDto);
    }

    public List<MokkojiRankingsResponseDto> geTmokkojiTopTen() {
        List<MokkojiRankingsResponseDto> list = new ArrayList<>();
        List<MokkojiRankings> topTen = mokkojiRankingService.getRankingTopTen();
        log.info("모꼬지 이름 탑텐입니다.{}",topTen);
        for (MokkojiRankings mokkojiRankings: topTen) {
            MokkojiRankDto mokkojiDto = new MokkojiRankDto(mokkojiRankings);
            List<Category> categoriesEntities = categoryService.getCategories(mokkojiRankings.getCategories());
            List<CategoryDto> categories = categoriesEntities
                    .stream().map(CategoryDto::new)
                    .collect(Collectors.toList());
            list.add(new MokkojiRankingsResponseDto(categories, mokkojiDto));
        }
        return list;

    }


    public MokkojiListResponseDto getMokkojiList(List<Integer> categories,int page, String keyword) {
        Page<Mokkoji> mokkojiList;
        if(categories == null || categories.size() == 0){
            mokkojiList = mokkojiService.getMokkojiList(page, keyword);
            log.info("모꼬지 목록조회 카테고리가 없을 때 입니다. {}", mokkojiList);
        }
        else{
            mokkojiList = categoryService.getMokkojiList(categories, page, keyword);
            log.info("모꼬지 목록조회 카테고리가 있을 때 입니다. {}", mokkojiList);
        }
        log.info("전체 아이템 수: {}", mokkojiList.getTotalElements());
        log.info("전체 페이지 수: {}", mokkojiList.getTotalPages());
        Map<Mokkoji, List<Category>> map = mokkojiCategoryService.findAllByMokkojis(mokkojiList);

        List<MokkojiCategoryDto> list = map.entrySet().stream().map(e -> {
            return new MokkojiCategoryDto(e.getValue(), e.getKey());
        }).collect(Collectors.toList());

        return new MokkojiListResponseDto(list, mokkojiList.getTotalPages());
    }
    //모꼬지 삭제로직
    @Transactional
    public void deleteMokkoji(String userId) {
        User user = userService.deleteMokkojiCheck(userId);
        Mokkoji mokkoji = user.getMokkojiId();
        mokkojiCategoryService.deleteMokkojiCategory(mokkoji);
        userService.deleteMokkojiUser(mokkoji);
        mokkojiService.deleteMokkoji(mokkoji);
    }
}
