package swm.spring.jpa.study.event;

import org.springframework.context.ApplicationEvent;
import swm.spring.jpa.study.entity.Post;

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
