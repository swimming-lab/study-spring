package swm.spring.jpa.post;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    /**
     * EntityGraph의 기본 fetch 전략은 eager이다.
     * @return
     */
    @EntityGraph(attributePaths = "post")
    Comment getReferenceById(Long id);

    /**
     * 여러개의 Proejction을 사용하려면 제너릭을 이용하면 된다.
     */
    <T> List<T> findByPost_Id(Long id, Class<T> type);
    // List<CommentSummary> findByPost_Id(Long id);
}
