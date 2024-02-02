package com.example.Pharmacy.Application.payment.service;

import com.example.Pharmacy.Application.payment.dto.ConfirmationValidationDTO;
import com.example.Pharmacy.Application.payment.dto.ConfirmationValidationResponse;
import com.example.Pharmacy.Application.payment.dto.RegisterUrlRequest;
import com.example.Pharmacy.Application.payment.dto.RegisterUrlResponse;
import com.example.Pharmacy.Application.payment.enums.PaymentStatus;
import com.example.Pharmacy.Application.payment.http.MpesaClient;
import com.example.Pharmacy.Application.payment.model.Payment;
import com.example.Pharmacy.Application.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MpesaClient mpesaClient;

    // Registered account numbers on our application
    private final List<String> allowedAccounts = List.of("ACCOUNT_ONE", "ACCOUNT_TWO", "ACCOUNT_THREE");

    public ConfirmationValidationResponse validatePayment(ConfirmationValidationDTO confirmationValidationDTO) {
        if (allowedAccounts.contains(confirmationValidationDTO.BillRefNumber())) {
            return paymentRepository.findById(Long.valueOf(confirmationValidationDTO.TransID()))
                    .map(mpesaPayment -> {
                        // Already registered return result code 0
                        return ConfirmationValidationResponse.builder()
                                .ResultCode("0")
                                .ResultDesc("Accepted")
                                .build();
                    })
                    .orElseGet(() -> {
                        // Record the payment and return result code 0
                        return savePayment(confirmationValidationDTO);
                    });
        } else {
            return ConfirmationValidationResponse.builder()
                    .ResultCode("C2B00012")
                    .ResultDesc("Rejected")
                    .build();
        }
    }

    private ConfirmationValidationResponse savePayment(ConfirmationValidationDTO confirmationValidationDTO) {
        Payment payment =  new Payment(); // ToDO: Add missing fields in Payment
        BeanUtils.copyProperties(confirmationValidationDTO, payment);
        payment.setPaymentStatus(PaymentStatus.VALIDATED);
        paymentRepository.save(payment);
        return ConfirmationValidationResponse.builder()
                .ResultCode("0")
                .ResultDesc("Accepted")
                .build();
    }

    // If payment validated, mark transaction as confirmed
    public ConfirmationValidationResponse confirmPaymentM(ConfirmationValidationDTO validationDTO) {
        if (allowedAccounts.contains(validationDTO.BillRefNumber())) {
            return paymentRepository.findById(Long.valueOf(validationDTO.TransID()))
                    .map(mpesaPayment -> {
                        mpesaPayment.setPaymentStatus(PaymentStatus.CONFIRMED);
                        paymentRepository.save(mpesaPayment);
                        return ConfirmationValidationResponse.builder()
                                .ResultCode("0")
                                .ResultDesc("Accepted")
                                .build();

                    })
                    .orElseGet(() -> savePayment(validationDTO));
        } else {
            return ConfirmationValidationResponse.builder()
                    .ResultCode("C2B00012")
                    .ResultDesc("Rejected")
                    .build();
        }
    }

    public RegisterUrlResponse registerUrl(RegisterUrlRequest registerUrlRequest) {
        return mpesaClient.registerUrl(registerUrlRequest);
    }
}
