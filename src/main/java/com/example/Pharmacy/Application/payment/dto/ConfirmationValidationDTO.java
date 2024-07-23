package main.java.com.example.Pharmacy.Application.payment.dto;

import java.math.BigDecimal;

//Once a customer makes a payment, mpesa will check whether you have enabled payment validation

public record ConfirmationValidationDTO(
        String TransactionType,
        String TransID,
        String TransTime,
        BigDecimal TransAmount,
        int BusinessShortCode,
        String BillRefNumber,
        String InvoiceNumber,
        BigDecimal OrgAccountBalance,
        String ThirdPartyTransID,
        String MSISDN, // Phone Number
        String FirstName,
        String MiddleName,
        String LastName
) {
}
