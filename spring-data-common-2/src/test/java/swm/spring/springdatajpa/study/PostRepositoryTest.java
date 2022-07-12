package swm.spring.springdatajpa.study;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import swm.spring.springdatajpa.entity.Post;
import swm.spring.springdatajpa.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    @Rollback(false)
    void crudRepositoryTest() {
        Post post = new Post();
        post.setTitle("SpringDataJpa");

        postRepository.save(post);

        List<Post> posts = postRepository.findAll();
        Page<Post> posts1 = postRepository.findAll(PageRequest.of(0, 10));
        Page<Post> springDataJpa = postRepository.findByTitleContains("SpringDataJpa", PageRequest.of(0, 20));
        long springDataJpa1 = postRepository.countByTitleContains("SpringDataJpa");
    }

    @Test
    @Rollback(false)
    void customRepositoryTest() {
        Post post = new Post();
        post.setTitle("SpringDataJpa");

        postRepository.save(post);
        postRepository.findMyPost();
        postRepository.delete(post);
        postRepository.flush();
    }
}
