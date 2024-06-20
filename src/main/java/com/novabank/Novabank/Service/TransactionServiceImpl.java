package com.novabank.Novabank.Service;

import com.novabank.Novabank.DTO.TransactionDto;
import com.novabank.Novabank.Entity.Transactions;
import com.novabank.Novabank.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public void saveTransactions(TransactionDto transactionDto) {
        Transactions transaction=Transactions.builder().transactionType(transactionDto.getTransactionType())
                .accountNumber(transactionDto.getAccountNumber()).amount(transactionDto.getAmount())
                .status("SUCCESS").build();
        transactionRepository.save(transaction);
        System.out.println("Transaction saved successfully!");
    }
}
