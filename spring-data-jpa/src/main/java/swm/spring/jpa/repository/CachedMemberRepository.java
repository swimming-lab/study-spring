package swm.spring.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swm.spring.jpa.entity.CachedMember;

public interface CachedMemberRepository extends JpaRepository<CachedMember, Long> {
}
