package swm.spring.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import swm.spring.jpa.study.repostiroy_custom.SimpleMyJpsRepository;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = SimpleMyJpsRepository.class)
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}

}
