package com.ces.intern.sunsama;

import com.ces.intern.sunsama.entity.UserEntity;
import com.ces.intern.sunsama.repository.UserRepository;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SunsamaApplication {

    private  static  final Logger log = LoggerFactory.getLogger(SunsamaApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(SunsamaApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper (){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new UserEntity(1L,"test user","123123",null)));
        };
    }

}
