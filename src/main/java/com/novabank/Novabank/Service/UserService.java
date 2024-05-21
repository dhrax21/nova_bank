package com.novabank.Novabank.Service;

import com.novabank.Novabank.DTO.BankResponse;
import com.novabank.Novabank.DTO.CreditDebitRequest;
import com.novabank.Novabank.DTO.EnquiryRequest;
import com.novabank.Novabank.DTO.UserRequest;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);

    BankResponse balanceEnquiry(EnquiryRequest request);
    String nameEnquiry(EnquiryRequest request);

    BankResponse creditAccount(CreditDebitRequest request);

    BankResponse debitAccount(CreditDebitRequest request);
}
