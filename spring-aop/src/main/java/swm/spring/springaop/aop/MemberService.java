package swm.spring.springaop.aop;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
public class MemberService {
    public void joinWithoutAop() {
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            memberSave();
        } finally {
            stopWatch.stop();
        }
    }

    public void joinWithAop() {
        memberSave();
    }

    private void memberSave() {
        System.out.println("save member...");
    }
}
