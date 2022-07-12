package swm.spring.springdatajpa.repository;

import swm.spring.springdatajpa.entity.Post;

import java.util.List;

public interface PostCustomRepository<T> {

    public List<Post> findMyPost();

    public void delete(T entity);
}
