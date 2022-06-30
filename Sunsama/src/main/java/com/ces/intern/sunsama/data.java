package com.ces.intern.sunsama;

import com.ces.intern.sunsama.entity.HashtagEntity;
import com.ces.intern.sunsama.entity.UserEntity;
import com.ces.intern.sunsama.repository.HashtagRepository;
import com.ces.intern.sunsama.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class data {
    private  static  final Logger logger = LoggerFactory.getLogger(data.class);
//    @Bean
//    CommandLineRunner initDatabase(HashtagRepository hashtagRepository, UserRepository userRepository) {
//        return args -> {
//            userRepository.save(new UserEntity(1L,"testUser", "123456", null));
//            userRepository.findAll()
//                    .forEach(user -> logger.info("Preloaded " + user));
//            hashtagRepository.save(new HashtagEntity(1L, "General", null));
//            hashtagRepository.findAll().forEach(i -> logger.info("Preloaded " + i.getId()));
//        };
//    }
}
