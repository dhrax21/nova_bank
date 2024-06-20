package com.novabank.Novabank.Utils;

import com.novabank.Novabank.DTO.BankResponse;

import java.time.Year;

public class AccountUtils {     //for generating random account numbers

    // account number=> currentYear + (random 6 digitNumber)


    public static final String ACCOUNT_EXISTS_CODE="001";
    public static final String ACCOUNT_EXISTS_MESSAGE="This user has already account created!";

    public static final String ACCOUNT_CREATION_SUCCESS="002";
    public static final String ACCOUNT_CREATION_MESSAGE="ACCOUNT_SUCCESSFULLY_CREATED";

    public static final String ACCOUNT_NOT_EXIST_CODE="003";
    public static final String ACCOUNT_NOT_EXIST_MESSAGE = "ACCOUNT IS NOT FOUND PLEASE CREATE FIRST";

    public static final String ACCOUNT_CREDITED_CODE="004";
    public static final String ACCOUNT_CREDITED_MESSAGE="ACCOUNT_CREDITED_SUCCESSFULLY;";
    public static final String INSUFFICIENT_BALANCE = "ACCOUNT HAVE INSUFFICIENT BALANCE";
    public static final String INSUFFICIENT_BALANCE_CODE="005";
    public static final String ACCOUNT_DEBITED_MESSAGE = "ACCOUNT_DEBITED_MESSAGE SUCCESSFULLY";

    public static final String ACCOUNT_DEBITED_CODE="006";
    public static String generateAccountNumber(){
        Year currYear=Year.now();
        int min=100000;
        int max=999999;

        int randNumber=(int) Math.floor(Math.random() * (max-min+1)+min);

        // make account number by adding random+currYear

        String year=String.valueOf(currYear);
        String rand=String.valueOf(randNumber);

        return year + rand;
    }




}
