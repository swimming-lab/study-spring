package swm.spring.springdatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import swm.spring.springdatajpa.repostiroy_custom.SimpleMyJpsRepository;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = SimpleMyJpsRepository.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
