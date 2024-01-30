package com.ssafy.backend.dagak.model.repository;

import com.ssafy.backend.dagak.model.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

    List<Calendar> findByUserId(String userId);

}