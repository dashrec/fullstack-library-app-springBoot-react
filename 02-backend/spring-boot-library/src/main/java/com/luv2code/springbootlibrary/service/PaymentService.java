package com.luv2code.springbootlibrary.service;
import com.luv2code.springbootlibrary.dao.PaymentRepository;
import com.luv2code.springbootlibrary.entity.Payment;
import com.luv2code.springbootlibrary.requestmodels.PaymentInfoRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
@Service
@Transactional
public class PaymentService {

    private PaymentRepository paymentrepository;

    @Autowired
    public PaymentService(PaymentRepository paymentrepository, @Value("${stripe.key.secret}") String secretKey){
        this.paymentrepository=paymentrepository;
        Stripe.apiKey=secretKey;
    }


    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException {

        List<String> paymentMethodTypes = new ArrayList<>();

        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();

        params.put("amount", paymentInfoRequest.getAmount()); // get amount type and currency the user has entered in frontend
        params.put("currency", paymentInfoRequest.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);

        return PaymentIntent.create(params);
    }

    public ResponseEntity<String> stripePayment(String userEmail) throws Exception { // after successful transaction sets amount to 0 to database
        Payment payment = paymentrepository.findByUserEmail(userEmail);

        if (payment == null) {
            throw new Exception("Payment information is missing");
        }
        payment.setAmount(00.00);
        paymentrepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
