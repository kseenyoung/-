package com.ssafy.backend.dagak.model.repository;

import com.ssafy.backend.dagak.model.domain.Dagak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DagakRepository extends JpaRepository<Dagak, Integer> {

    List<Dagak> findDagaksByUserId(String userId);
}
