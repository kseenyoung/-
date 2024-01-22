package com.ssafy.backend.alarm.model.repository;

import com.ssafy.backend.alarm.model.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {



}
