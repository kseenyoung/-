package com.ssafy.backend.alarm.model.repository;

import com.ssafy.backend.alarm.model.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

    public List<Alarm> findAllByUserIdOrderByIsCheckedAscCreatedDateDesc(String userId);

    public List<Alarm> findByUserIdAndIsChecked(String userId, Integer isChecked);

    Optional<Alarm> findAlarmByUserIdAndRequestedUserIdAndTagId(String userId, String requestUserId, int tagId);

    Alarm findByUserIdAndRequestedUserIdAndTagIdAndIsChecked(String userId, String requestedUserId, Integer tagId, Integer isChecked);


    Optional<Alarm> findOptionalAlarmByUserIdAndRequestedUserIdAndTagIdAndIsChecked(String userId, String requestedUserId, Integer tagId, Integer isChecked);
}