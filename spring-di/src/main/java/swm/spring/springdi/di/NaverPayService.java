package swm.spring.springdi.di;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("mainPayService")
public class NaverPayService implements PayService {
    @Override
    public void payment() {
        System.out.println("Naver Pay");
    }
}
