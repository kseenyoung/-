package com.ssafy.backend.mokkoji.model.repository;

import com.ssafy.backend.mokkoji.model.domain.MokkojiRankings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MokkojiRankingsRepository extends JpaRepository<MokkojiRankings,Integer> {

    @Query(value = "SELECT m.mokkoji_id, m.mokkoji_name, m.leader_id, t.total_memory_time, GROUP_CONCAT(c.category_id) as categories, " +
            "(SELECT COUNT(*) FROM mokkoji_rankings mr WHERE t.total_memory_time <= mr.total_memory_time) AS 'rank' " +
            "FROM mokkoji m " +
            "JOIN total_time_per_mokkoji t ON m.mokkoji_id = t.mokkoji_id " +
            "JOIN mokkoji_category mc ON m.mokkoji_id = mc.mokkoji_id " +
            "JOIN category c ON mc.category_id = c.category_id " +
            "WHERE m.mokkoji_name = :mokkojiName " +
            "GROUP BY m.mokkoji_id " +
            "ORDER BY t.total_memory_time DESC", nativeQuery = true)
    List<MokkojiRankings> getGuildRankListByName(@Param("mokkojiName") String mokkojiName);


    @Query(value = "SELECT m.mokkoji_id, m.mokkoji_name, m.leader_id, t.total_memory_time, GROUP_CONCAT(c.category_id) as categories, " +
            "(SELECT COUNT(*) FROM mokkoji_rankings mr WHERE t.total_memory_time <= mr.total_memory_time) AS 'rank' " +
            "FROM mokkoji m " +
            "JOIN total_time_per_mokkoji t ON m.mokkoji_id = t.mokkoji_id " +
            "JOIN mokkoji_category mc ON m.mokkoji_id = mc.mokkoji_id " +
            "JOIN category c ON mc.category_id = c.category_id " +
            "GROUP BY m.mokkoji_id " +
            "ORDER BY t.total_memory_time DESC limit 10", nativeQuery = true)
    List<MokkojiRankings> getGuildRankList();
}
