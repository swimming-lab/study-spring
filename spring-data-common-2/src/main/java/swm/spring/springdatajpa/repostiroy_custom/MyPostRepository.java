package swm.spring.springdatajpa.repostiroy_custom;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import swm.spring.springdatajpa.entity.Post;


@Repository
public interface MyPostRepository extends MyJpaRepository<Post, Long>, QuerydslPredicateExecutor<Post> {

}
