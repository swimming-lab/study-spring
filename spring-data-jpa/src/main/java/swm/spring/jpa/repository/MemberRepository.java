package swm.spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swm.spring.jpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
