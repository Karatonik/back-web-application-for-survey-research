package pl.mateusz.kalksztejn.survey.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mateusz.kalksztejn.survey.services.interfaces.PaymentService;
@Service
public class PaymentServiceImp implements PaymentService {

    @Autowired
    public PaymentServiceImp() {
    }
}
