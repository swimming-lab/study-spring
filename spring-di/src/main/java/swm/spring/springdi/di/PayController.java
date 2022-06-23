package swm.spring.springdi.di;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class PayController {
    private final PayService payService;

    public PayController(@Qualifier("mainPayService") PayService payService) {
//    public PayController(PayService payService) {
        this.payService = payService;
    }
}
