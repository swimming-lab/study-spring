package swm.spring.springdatajpa.event;

import org.springframework.context.ApplicationEvent;
import swm.spring.springdatajpa.entity.Post;

public class PostPublishedEvent extends ApplicationEvent {

    private final Post post;

    public PostPublishedEvent(Object source) {
        super(source);
        this.post = (Post) source;
    }

    public Post getPost() {
        return this.post;
    }
}
