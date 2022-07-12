package swm.spring.springdatajpa.study;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import swm.spring.springdatajpa.event.PostListener;

@Configuration
public class PostEventTestConfig {

    @Bean
    public PostListener postListener() {
        return new PostListener();
    }
}
