package com.ces.intern.sunsama.repository;

import com.ces.intern.sunsama.entity.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtagRepository extends JpaRepository<HashtagEntity,Long> {
    int countByName(String name);
    @Query(value = "SELECT * FROM hashtag JOIN task_hashtag ON hashtag.id=task_hashtag.hashtag_id WHERE task_hashtag.task_id=:taskId",
    nativeQuery = true)
    List<HashtagEntity> getHashtagByTaskId(@Param("taskId") long id);
}
