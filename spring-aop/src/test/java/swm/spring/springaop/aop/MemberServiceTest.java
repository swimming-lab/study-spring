package swm.spring.springaop.aop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Test
    void joinWithoutAopTest() {
        memberService.joinWithoutAop();
    }

    @Test
    void joinWithAopTest() {
        memberService.joinWithAop();
    }
}
