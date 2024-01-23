package com.ssafy.backend.alarm.model.repository;

import com.ssafy.backend.alarm.model.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

    public List<Alarm> findAllByUserId(String userId);

    public List<Alarm> findByUserIdAndIsChecked(String userId, Integer isChecked);
}