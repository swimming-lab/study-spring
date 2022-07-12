package swm.spring.jpa.study.repository;

import org.springframework.beans.factory.annotation.Autowired;
import swm.spring.jpa.study.entity.Post;

import javax.persistence.EntityManager;
import java.util.List;

public class PostCustomRepositoryImpl implements PostCustomRepository<Post> {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Post> findMyPost() {
        System.out.println("custom findMyPost");
        return entityManager.createQuery("select p from Post p").getResultList();
    }

    @Override
    public void delete(Post entity) {
        System.out.println("custom delete");
        entityManager.remove(entity);
    }
}
