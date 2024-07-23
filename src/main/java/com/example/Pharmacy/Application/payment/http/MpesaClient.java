package main.java.com.example.Pharmacy.Application.payment.http;

import main.java.com.example.Pharmacy.Application.payment.dto.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface MpesaClient {

    @PostExchange("/mpesa/c2b/v1/registerurl")
    RegisterUrlResponse registerUrl(@RequestBody RegisterUrlRequest registerUrlRequest);

    @PostExchange("/mpesa/stkpush/v1/processrequest")
    STKNIPushResponse mpesaExpress(@RequestBody STKNIPushPayload stkniPushPayload);

    @PostExchange("/mpesa/stkpushquery/v1/query")
    STKNIPushQueryResponse mpesaExpressQuery(@RequestBody STKNIPushQuery pushQuery);
}
