package com.ssafy.backend.mokkoji.service;

import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.mokkoji.model.domain.Category;
import com.ssafy.backend.mokkoji.model.domain.MokkojiRankings;
import com.ssafy.backend.mokkoji.model.dto.CategoryDto;
import com.ssafy.backend.mokkoji.model.dto.MokkojiDto;
import com.ssafy.backend.mokkoji.model.dto.MokkojiRankingsResponseDto;
import com.ssafy.backend.mokkoji.model.repository.CategoryRepository;
import com.ssafy.backend.mokkoji.model.repository.MokkojiRankingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class MokkojiServiceImpl implements MokkojiService {
    private final MokkojiRankingsRepository mokkojiRankingsRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public void createMokkoji() {

    }

    @Override
    public List<MokkojiRankingsResponseDto> geTmokkojiRnakings() {

        List<MokkojiRankingsResponseDto> list = new ArrayList<>();

        List<MokkojiRankings> all = mokkojiRankingsRepository.getGuildRankList();

        for (MokkojiRankings mokkojiRankings: all) {
            MokkojiDto mokkojiDto = new MokkojiDto(mokkojiRankings);
            List<Category> categories = categoryRepository.findAllById(mokkojiRankings.getCategories());
            List<CategoryDto> ca = categories.stream().map(CategoryDto::new).collect(Collectors.toList());
            list.add(new MokkojiRankingsResponseDto(ca, mokkojiDto));
        }

        return list;

    }

    @Override
    public MokkojiRankingsResponseDto searchRanking(String mokkojiName) {
        List<MokkojiRankings> guildRankListByName = mokkojiRankingsRepository.getGuildRankListByName(mokkojiName);
        if(guildRankListByName.size() <= 0) throw new MyException("길드가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        MokkojiRankings mokkojiRankings = guildRankListByName.get(0);

        MokkojiDto mokkojiDto = new MokkojiDto(mokkojiRankings);
        List<Category> categories = categoryRepository.findAllById(mokkojiRankings.getCategories());
        List<CategoryDto> ca = categories.stream().map(CategoryDto::new).collect(Collectors.toList());
        return new MokkojiRankingsResponseDto(ca, mokkojiDto);
    }
}
