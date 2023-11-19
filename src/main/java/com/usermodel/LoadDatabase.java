package com.usermodel;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
    

    @Bean
    public CommandLineRunner dataInitialize(UserRepository userRepository) {
        return args -> {
        userRepository.save(new UserModel("tristin", "eason", "boogey", "abc123"));
        userRepository.save(new UserModel("maylin", "reynold", "mayday", "0929"));
        userRepository.save(new UserModel("annarae", "reynold", "little", "big head"));
        };
    }
}
