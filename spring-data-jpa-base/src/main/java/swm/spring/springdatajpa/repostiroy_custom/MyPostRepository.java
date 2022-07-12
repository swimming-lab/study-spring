package swm.spring.jpa.study.repostiroy_custom;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import swm.spring.jpa.study.entity.Post;


@Repository
public interface MyPostRepository extends MyJpaRepository<Post, Long>, QuerydslPredicateExecutor<Post> {

}
