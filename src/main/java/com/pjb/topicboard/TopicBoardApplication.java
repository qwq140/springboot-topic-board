package com.pjb.topicboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TopicBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(TopicBoardApplication.class, args);
    }

}
