package swm.spring.jpa.study.repository;

import swm.spring.jpa.study.entity.Post;

import java.util.List;

public interface PostCustomRepository<T> {

    public List<Post> findMyPost();

    public void delete(T entity);
}
