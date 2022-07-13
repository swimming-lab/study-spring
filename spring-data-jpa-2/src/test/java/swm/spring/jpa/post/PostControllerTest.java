package swm.spring.jpa.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PostControllerTest {

    @Autowired
    PostRepository postRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    void persist_merge() {
        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post);// persist

        assertThat(entityManager.contains(post)).isTrue();
        assertThat(entityManager.contains(savedPost)).isTrue();
        assertThat(post == savedPost);

        Post postUpdate = new Post();
        postUpdate.setId(post.getId());
        postUpdate.setTitle("hibernate");
        Post updatedPost = postRepository.save(postUpdate);// merge

        /**
         * 매우 중요!!
         * merge해서 리턴받는 객체와 merge를 요청한 객체는 같지 않다.
         * 반드시 리턴받는 객체를 사용해라(persistent 상태의 객체를 사용)
         */
        assertThat(entityManager.contains(updatedPost)).isTrue();
        assertThat(entityManager.contains(postUpdate)).isFalse();
        assertThat(updatedPost == postUpdate);

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void save() {
        Post post = new Post();
        post.setTitle("jpa");
        Post savedPost = postRepository.save(post);// persist

        Post postUpdate = new Post();
        postUpdate.setId(post.getId());
        postUpdate.setTitle("hibernate");
        Post updatedPost = postRepository.save(postUpdate);// merge

        updatedPost.setTitle("whiteship");

        List<Post> all = postRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void findByTitleStartsWith() {
        Post post = new Post();
        post.setTitle("Spring Data Jpa");
        postRepository.save(post);

        List<Post> all = postRepository.findByTitleStartsWith("Spring");
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void findByTitle() {
        Post post = new Post();
        post.setTitle("Spring");
        postRepository.save(post);

        List<Post> all = postRepository.findByTitle("Spring");
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void findByTitleSort() {
        Post post = new Post();
        post.setTitle("Spring");
        postRepository.save(post);

        /**
         * 정렬조건에서 함수를 호출하고 싶으면 JpaSort.unsafe로 우회할 수 있다.
         */
//        List<Post> all = postRepository.findByTitle("Spring", Sort.by("LENGTH(title"));
        List<Post> all = postRepository.findByTitle("Spring", JpaSort.unsafe("LENGTH(title)"));
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    void updateTitle() {
        Post post = new Post();
        post.setTitle("Spring");
        Post spring = postRepository.save(post);

        String hibernate = "hibernate";
        int update = postRepository.updateTitle(hibernate, spring.getId());
        assertThat(update).isEqualTo(1);

        /**
         * DB조회하지 않는다. 이유는 persistent에서(1차 캐싱) 중인 데이터가 존재하기 때문
         * 해결하기 위해서는 persistent 상태를 clear해줘야 한다.
         * repository에서 @Modifying(clearAutomatically = true) 추가하면 자동적으로 clear해준다.
         */
        Optional<Post> byId = postRepository.findById(spring.getId());
        Post post1 = byId.get();
        assertThat(post1.getTitle()).isEqualTo(hibernate);
    }

    @Test
    void updateTitle2() {
        Post post = new Post();
        post.setTitle("Spring");
        Post spring = postRepository.save(post);

        String hibernate = "hibernate";
        spring.setTitle(hibernate);

        /**
         * 위 문제를 간단하게 해결하기 위한 방법(팀원들이 jpa를 모두 이해하고 있지 않은 경우 리뷰가 쉽지 않기 때문에)
         * persistent 상태의 객체를 set하고 나서 select가 필요한 상황에서 update를 자동적으로 해준다.
         * 이게 위에보다 간단하다.
         */
        List<Post> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(hibernate);
    }
}