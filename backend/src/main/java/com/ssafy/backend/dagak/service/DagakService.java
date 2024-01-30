package com.ssafy.backend.dagak.service;

import com.ssafy.backend.dagak.model.dto.DagakDto;
import com.ssafy.backend.dagak.model.dto.GakDto;

import java.util.List;

public interface DagakService {
    int createDagak(DagakDto dagakDto);

    void createGak(List<GakDto> gaks);
}
