package swm.spring.jpa.study.event;

import org.springframework.context.event.EventListener;

//public class PostListener implements ApplicationListener<PostPublishedEvent> {
public class PostListener {

    /**
     * 두가지 리스너 등록 방법을 제공
     * 1. ApplicationListener를 구현하고 onApplicationEvent를 오버라이드한다.
     * 2. 메소드에 @EventListener를 붙여서 바로 사용한다.
     * @param event
     */
    @EventListener
    public void onApplicationEvent(PostPublishedEvent event) {
        System.out.println("---------------------");
        System.out.println(event.getPost().getTitle());
        System.out.println("---------------------");
    }
}
