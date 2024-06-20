package com.novabank.Novabank.Service;

import com.novabank.Novabank.DTO.*;
import com.novabank.Novabank.Entity.User;
import com.novabank.Novabank.Repository.UserRepository;
import com.novabank.Novabank.Utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;


    @Autowired
    TransactionService transactionService;


    @Override
    public BankResponse createAccount(UserRequest userRequest) {

//        Creating new account -> saving new user to the db
//        Check if account already exists

        if(userRepository.existsByEmail(userRequest.getEmail())){
            return BankResponse.builder().
                    responseCode(AccountUtils.ACCOUNT_EXISTS_CODE).
                    responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User user=User.builder().firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();

        User savedUser=userRepository.save(user);
        return BankResponse.builder().
                responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS).
                responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE).
                accountInfo(AccountInfo.builder().
                        accountBalance(savedUser.getAccountBalance()).
                                accountNumber(savedUser.getAccountNumber()).
                        accountName(savedUser.getFirstName()+" "+savedUser.getLastName()+" "+savedUser.getOtherName())
                        .build())
                .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist)
        {
            return BankResponse.builder().
                    responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE).
                    responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE).
                    accountInfo(null).build();

        }

        User foundUser=userRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder().
                responseCode(AccountUtils.ACCOUNT_EXISTS_CODE).
        accountInfo(AccountInfo.builder().
                accountBalance(foundUser.getAccountBalance())
                .accountNumber(foundUser.getAccountNumber())
                .accountName(foundUser.getFirstName()+" "+foundUser.getLastName()).build()).build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist)
        {
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }

        User foundUser=userRepository.findByAccountNumber(request.getAccountNumber());
        return foundUser.getFirstName()+" "+foundUser.getLastName();
    }

    @Override
    public BankResponse creditAccount(CreditDebitRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist)
        {
            return BankResponse.builder().
                    responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE).
                    responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE).
                    accountInfo(null).build();

        }

        User user=userRepository.findByAccountNumber(request.getAccountNumber());
        user.setAccountBalance(user.getAccountBalance().add(request.getAmount()));
        userRepository.save(user);
        //save transactions
        TransactionDto transactionDto=TransactionDto.builder()
                .accountNumber(user.getAccountNumber()).transactionType("CREDIT").
                amount(request.getAmount())
                .build();
        transactionService.saveTransactions(transactionDto);

        return BankResponse.builder().
                responseCode(AccountUtils.ACCOUNT_CREDITED_CODE).
                responseMessage(AccountUtils.ACCOUNT_CREDITED_MESSAGE).accountInfo(AccountInfo.builder().accountName(user.getFirstName()+" "+user.getLastName())
                        .accountNumber(user.getAccountNumber()).accountBalance(user.getAccountBalance()).build()).build();



    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest request) {
        boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
        if (!isAccountExist) {
            return BankResponse.builder().
                    responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE).
                    responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE).
                    accountInfo(null).build();

        }

        User user = userRepository.findByAccountNumber(request.getAccountNumber());
        BigDecimal currBalance = user.getAccountBalance();
        BigDecimal debitBalance = request.getAmount();

        if (currBalance.compareTo(debitBalance) < 0) {
            return BankResponse.builder().responseMessage(AccountUtils.INSUFFICIENT_BALANCE).build();
        }


        else {
            user.setAccountBalance(user.getAccountBalance().subtract(request.getAmount()));
            userRepository.save(user);

            //save for debit account also
            TransactionDto transactionDto=TransactionDto.builder()
                    .accountNumber(user.getAccountNumber()).transactionType("DEBIT").
                    amount(request.getAmount())
                    .build();


            transactionService.saveTransactions(transactionDto);
            return BankResponse.builder().
                    responseCode(AccountUtils.ACCOUNT_DEBITED_CODE).
                    responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE).accountInfo(AccountInfo.builder().accountName(user.getFirstName() + " " + user.getLastName())
                            .accountNumber(user.getAccountNumber()).accountBalance(user.getAccountBalance()).build()).build();
        }
    }


    @Override
    public BankResponse transfer(TransferRequest request) {
        //check the account to debit
        //check if debit amount <current balance
        //debit the account
        //get the account to credit

        boolean isDestAccountExist=userRepository.existsByAccountNumber(request.getDestinationAccountNumber());

        if(!isDestAccountExist){
            return BankResponse.builder().responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE).accountInfo(null).build();
        }

        User userSourceAccount=userRepository.findByAccountNumber(request.getSourceAccountNumber());

        if(request.getAmount().compareTo(userSourceAccount.getAccountBalance())>0){
            return BankResponse.builder().responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE).
                    responseMessage(AccountUtils.INSUFFICIENT_BALANCE).accountInfo(null).build();

        }

        userSourceAccount.setAccountBalance(userSourceAccount.getAccountBalance().subtract(request.getAmount()));
        userRepository.save(userSourceAccount);

        User destinationAccountUser=userRepository.findByAccountNumber(request.getDestinationAccountNumber());
        destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(request.getAmount()));

        return null;
    }

}
