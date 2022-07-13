package swm.spring.jpa.post;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleStartsWith(String title);

    @Query(value = "SELECT p FROM Post AS p WHERE p.title = ?1")
    List<Post> findByTitle(String title);

    /**
     * alias, Named Parameter, SpEL
     */
    @Query(value = "SELECT p FROM #{#entityName} AS p WHERE p.title = :title")
//    @Query(value = "SELECT p, p.title AS pTitle FROM Post AS p WHERE p.title = ?1")
    List<Post> findByTitle(@Param("title") String keyword, Sort sort);

    /**
     * 업데이트 이후 persistent 상태를 clear해주는 옵션
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Post AS p SET p.title = ?1 WHERE p.id = ?2")
    int updateTitle(String title, Long id);
}
