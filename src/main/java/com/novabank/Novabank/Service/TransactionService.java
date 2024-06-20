package com.novabank.Novabank.Service;

import com.novabank.Novabank.DTO.TransactionDto;
import com.novabank.Novabank.Entity.Transactions;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {
    void saveTransactions(TransactionDto transactionsdto);
}
