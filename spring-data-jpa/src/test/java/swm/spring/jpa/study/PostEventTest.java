package swm.spring.jpa.study;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import swm.spring.jpa.study.entity.Post;
import swm.spring.jpa.study.event.PostPublishedEvent;
import swm.spring.jpa.study.repository.PostRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(PostEventTestConfig.class)
public class PostEventTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void eventTest_1() {
        Post post = new Post();
        post.setTitle("event!!");

        // 직접 이벤트를 발행한다.
        PostPublishedEvent event = new PostPublishedEvent(post);
        applicationContext.publishEvent(event);
    }

    @Test
    void eventTest_2() {
        Post post = new Post();
        post.setTitle("event!!");

//        PostPublishedEvent event = new PostPublishedEvent(post);
//        applicationContext.publishEvent(event);

        // save할 때 이벤트를 발생시킨다.
        postRepository.save(post.publish());
    }
}
