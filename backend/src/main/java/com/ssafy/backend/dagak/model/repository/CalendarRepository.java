package com.ssafy.backend.dagak.model.repository;

import com.ssafy.backend.dagak.model.domain.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {

    List<Calendar> findByUserId(String userId);

    Calendar findCalendarByCalendarDate(LocalDate date);

    List<Calendar> findAllByDagakId(Integer dagakId);

    void deleteByCalendarDagakId(Integer dagakId);

    Optional<Calendar> findCalendarByCalendarDateAndUserId(LocalDate today, String userId);
}