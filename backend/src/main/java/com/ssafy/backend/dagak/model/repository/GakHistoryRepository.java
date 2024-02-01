package com.ssafy.backend.dagak.model.repository;

import com.ssafy.backend.dagak.model.domain.GakHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface GakHistoryRepository extends JpaRepository<GakHistory, Integer> {

    GakHistory findByGakIdAndCreatedDate(Integer dagakId, LocalDate createdDate);

}
