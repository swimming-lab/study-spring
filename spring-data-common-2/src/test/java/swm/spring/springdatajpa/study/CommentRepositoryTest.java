package swm.spring.springdatajpa.study;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import swm.spring.springdatajpa.entity.Comment;
import swm.spring.springdatajpa.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class CommentRepositoryTest {

    @Autowired
    CommentRepository commentRepository;

    @Test
    @Rollback(value = false)
    void crudTest() {
        Comment comment = new Comment();
        comment.setComment("Hello Comment!");

        commentRepository.save(comment);
        commentRepository.findAll();
        commentRepository.count();

        Optional<Comment> byId = commentRepository.findById(100L);
        byId.orElseThrow(IllegalArgumentException::new);
    }

    @Test
    @Rollback(value = false)
    void test() {
        Comment comment = new Comment();
        comment.setComment("Hello Comment!");
        comment.setLikeCount(100);

        commentRepository.save(comment);

        List<Comment> comment1 = commentRepository.findByCommentContains("comment");
        System.out.println(comment1.size());

        List<Comment> comment2 = commentRepository.findByCommentContainsIgnoreCase("comment");
        System.out.println(comment2.size());

        List<Comment> comment3 = commentRepository.findByCommentContainsIgnoreCaseOrderByLikeCountDesc("comment");
        System.out.println(comment3.get(0).getLikeCount());

        Page<Comment> comment4 = commentRepository.findByCommentContainsIgnoreCase("comment", PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "likeCount")));
        System.out.println(comment4.getNumberOfElements());
    }
}
