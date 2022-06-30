package com.ces.intern.sunsama;


import com.ces.intern.sunsama.repository.HashtagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class data {
    private  static  final Logger logger = LoggerFactory.getLogger(data.class);
//    @Bean
//    CommandLineRunner initDatabase(HashtagRepository hashtagRepository) {
//        return args -> {
////
////            logger.info("Preloading " + hashtagRepository.save(new HashtagEntity("UI/UX")));
////            logger.info("Preloading " + hashtagRepository.save(new HashtagEntity("Backend")));
////            logger.info("Preloading " + hashtagRepository.save(new HashtagEntity("Gt")));
//        };
//    }
}
