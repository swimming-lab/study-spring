package swm.spring.jpa.study;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import swm.spring.jpa.study.event.PostListener;

@Configuration
public class PostEventTestConfig {

    @Bean
    public PostListener postListener() {
        return new PostListener();
    }
}
