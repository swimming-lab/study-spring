package swm.spring.jpa.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@SpringBootTest
class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    void entityGraph() {
        commentRepository.getReferenceById(1l);

        System.out.println("=================");

        commentRepository.findById(1l);
    }

    @Test
    void projection() {
        Post post = new Post();
        post.setTitle("jpa");
        Post save = postRepository.save(post);

        Comment comment = new Comment();
        comment.setPost(save);
        comment.setComment("spring data jpa projection");
        comment.setUp(10);
        comment.setDown(1);
        commentRepository.save(comment);

        // commentRepository.findByPost_Id(save.getId()).forEach(c -> {
        //    System.out.println("============");
        //    System.out.println(c.getVotes());
        // });

        commentRepository.findByPost_Id(save.getId(), CommentOnly.class).forEach(c -> {
            System.out.println("============");
            System.out.println(c.getComment());
        });
    }

    @Test
    void specs() {
        /**
         * QueryDSL처럼 프로젝트 환경설정을 통해 원하는 스펙을 미리 정의할 수 있다.
         * 설정값 오류로 인해 진행하지 못했지만, QueryDSL과 같이 사용할 경우,
         * 직접 JPA를 작성하지 않더라도 이미 만들어진 기능들을 스펙과 함께 조합하여 사용하여
         * 효과적으로 비즈니스를 구현할 수 있다.
         *
         * 이 부분은 추후 maven으로 설정하여 테스트 해보자.!(gradle은 실패)
         */
//        commentRepository.findAll(isBest().or(isGood()), PageRequest.of(0, 10));
    }
}