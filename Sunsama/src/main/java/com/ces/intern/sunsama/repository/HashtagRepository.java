package com.ces.intern.sunsama.repository;

import com.ces.intern.sunsama.entity.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<HashtagEntity,Long> {
    int countByName(String name);
}
