package swm.spring.springdatajpa.study;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import swm.spring.springdatajpa.entity.Post;
import swm.spring.springdatajpa.entity.QPost;
import swm.spring.springdatajpa.repostiroy_custom.MyPostRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SimpleMyJpaRepositoryTest {

    @Autowired
    MyPostRepository myPostRepository;

    @Test
    void test() {
        Post post = new Post();
        post.setTitle("qwer");

        assertThat(myPostRepository.contains(post)).isFalse();

        myPostRepository.save(post);

        assertThat(myPostRepository.contains(post)).isTrue();

        myPostRepository.delete(post);
        myPostRepository.flush();
    }

    @Test
    void queryDSLTest() {
        QPost post = QPost.post;
        Predicate predicate = post
                .title.contains("test");

        Optional<Post> one = myPostRepository.findOne(predicate);
        assertThat(one).isEmpty();
    }
}
