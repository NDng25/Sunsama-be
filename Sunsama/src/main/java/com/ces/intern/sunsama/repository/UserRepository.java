package com.ces.intern.sunsama.repository;

import com.ces.intern.sunsama.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}
