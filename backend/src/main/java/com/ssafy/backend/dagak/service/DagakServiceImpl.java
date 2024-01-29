package com.ssafy.backend.dagak.service;

import com.ssafy.backend.dagak.model.domain.Dagak;
import com.ssafy.backend.dagak.model.domain.Gak;
import com.ssafy.backend.dagak.model.dto.DagakDto;
import com.ssafy.backend.dagak.model.dto.GakDto;
import com.ssafy.backend.dagak.model.repository.DagakRepository;
import com.ssafy.backend.dagak.model.repository.GakRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DagakServiceImpl implements DagakService {

    @Autowired
    DagakRepository dagakRepository;

    @Autowired
    GakRepository gakRepository;

    @Override
    public int createDagak(DagakDto dagakDto) {
        log.info("dagak : {}", dagakDto);
        return dagakRepository.save(
                Dagak.builder()
                        .user_id(dagakDto.getUserId())
                        .total_time(dagakDto.getTotalTime())
                        .build()
        ).getDagak_id();
    }

    @Override
    public void createGak(List<GakDto> gaks) {
        log.info("gak : {}", gaks);
        List<Gak> gakEntities = new ArrayList<>();
        for (GakDto gak : gaks) {
            Gak build = Gak.builder()
                    .userId(gak.getUserId())
                    .gakOrder(gak.getGakOrder())
                    .runningTime(gak.getRunningTime())
                    .dagakId(gak.getDagakId())
                    .build();
            gakEntities.add(build);
        }

        gakRepository.saveAll(gakEntities);
    }
}