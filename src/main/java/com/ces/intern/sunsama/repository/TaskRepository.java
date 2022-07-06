package com.ces.intern.sunsama.repository;

import com.ces.intern.sunsama.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query(value = "SELECT * FROM task JOIN task_hashtag ON task.id = task_hashtag.task_id WHERE id=:task_id AND hashtag_id = :hashtag_id", nativeQuery = true)
    TaskEntity getTaskHasHashtag(@Param("task_id") long taskId, @Param("hashtag_id") long hashtagId);
}
