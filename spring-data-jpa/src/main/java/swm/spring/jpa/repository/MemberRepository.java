package swm.spring.jpa.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swm.spring.jpa.entity.Member;

import java.awt.print.Pageable;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("select distinct m from Member m left join fetch m.articles")
    List<Member> findAllJPQLFetch();

    @Query("select distinct m from Member m left join fetch m.articles")
    List<Member> findAllJPQLFetch(PageRequest pageRequest);

    @EntityGraph(attributePaths = {"articles"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select distinct m from Member m left join m.articles")
    List<Member> findAllEntityGraph();
}
