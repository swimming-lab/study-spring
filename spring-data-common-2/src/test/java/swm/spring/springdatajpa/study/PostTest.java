package swm.spring.springdatajpa.study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import swm.spring.springdatajpa.entity.Comment;
import swm.spring.springdatajpa.entity.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@DataJpaTest
public class PostTest {

    @PersistenceContext
    EntityManager entityManager;

    Post post;
    Comment comment;

    @BeforeEach
    void setup() {
        post = new Post();
        post.setTitle("게시글 작성한다.");

        comment = new Comment();
        comment.setComment("안녕?");

        Comment comment2 = new Comment();
        comment2.setComment("안녕?");

        post.addComment(comment);
        post.addComment(comment2);

        entityManager.persist(post);    // 이렇게 Post만 저장할 시 cascade 옵션이 없으면 Comment는 저장되지 않는다.
        entityManager.flush();
        entityManager.clear();
    }


    @Test
    @Rollback(false)
    void hibernate_cascade() {
        Post post = new Post();
        post.setTitle("게시글 작성한다.11");

        Comment comment1 = new Comment();
        comment1.setComment("안녕?11");

        Comment comment2 = new Comment();
        comment2.setComment("안녕?11122");

        post.addComment(comment1);
        post.addComment(comment2);

        entityManager.persist(post);    // 이렇게 Post만 저장할 시 cascade 옵션이 없으면 Comment는 저장되지 않는다.
        entityManager.flush();

        /**
         * cascade 옵션이 존재하면 아래와 같이 한번에 다 저장된다.
         * (현재 cascade옵션이 Post객체에서 가지고 있기 때문에 post 객체를 저장할 때 다 저장된다)
         * Hibernate:
         *     insert
         *     into
         *         post
         *         (title, id)
         *     values
         *         (?, ?)
         * Hibernate:
         *     insert
         *     into
         *         comment
         *         (comment, post_id, id)
         *     values
         *         (?, ?, ?)
         * Hibernate:
         *     insert
         *     into
         *         comment
         *         (comment, post_id, id)
         *     values
         *         (?, ?, ?)
         */
    }

    @Test
    @Rollback(false)
    void fetchTest() {
        Post post1 = entityManager.find(Post.class, post.getId());
        post1.getComments().stream().forEach(c -> {
            System.out.println(c.getComment());
        });

        //System.out.println(post1.getComments());
        //entityManager.flush();
        //entityManager.clear();

        Comment comment1 = entityManager.find(Comment.class, comment.getId());
        System.out.println(comment1.getComment());
    }
}
