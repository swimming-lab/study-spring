package swm.spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swm.spring.jpa.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
