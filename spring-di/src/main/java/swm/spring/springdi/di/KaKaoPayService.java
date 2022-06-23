package swm.spring.springdi.di;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class KaKaoPayService implements PayService {
    @Override
    public void payment() {
        System.out.println("Kakao Pay");
    }
}
