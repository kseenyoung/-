package com.ssafy.backend.dagak.service;

import com.ssafy.backend.dagak.model.dto.DagakDto;
import com.ssafy.backend.dagak.model.dto.GakDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DagakFacade {

    @Autowired
    private DagakService dagakService;

    @Transactional
    public void createDagak(DagakDto dagakDto, List<GakDto> gaks){

        int dagakId = dagakService.createDagak(dagakDto);

        // 각 생성
        for(GakDto gak: gaks){
            gak.setDagakId(dagakId);
        }

        dagakService.createGak(gaks);
    }
}
