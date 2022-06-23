package com.ces.intern.sunsama.reponsitory;

import com.ces.intern.sunsama.entity.HashtagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<HashtagEntity,Long>
{
Integer countByName(String name);
}
