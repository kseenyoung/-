package com.ssafy.backend.dagak.model.repository;

import com.ssafy.backend.dagak.model.domain.Gak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GakRepository extends JpaRepository<Gak, Integer> {

    public List<Gak> findAllByDagakId(Integer dagakId);

}
