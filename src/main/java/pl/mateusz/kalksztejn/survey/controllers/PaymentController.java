package pl.mateusz.kalksztejn.survey.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mateusz.kalksztejn.survey.services.interfaces.PaymentService;

@RestController
@RequestMapping("api/pay")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentController {

    PaymentService paymentService;
    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
