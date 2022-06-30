package com.ces.intern.sunsama;

import com.ces.intern.sunsama.entity.UserEntity;
import com.ces.intern.sunsama.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SunsamaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunsamaApplication.class, args);
    }



    @Bean
    public ModelMapper modelMapper (){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }


}
