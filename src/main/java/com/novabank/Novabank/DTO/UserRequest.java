package com.novabank.Novabank.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    private String address;
    private String stateOfOrigin;
    private String email;
    private String password;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String status;
}


//api testing purpose
//"firstName":"yogesh"
//        "lastName":"panda",
//        "otherName":"ludi",
//        "gender":"female",
//        "address":"newyork",
//        "stateOfOrigin":"newJersy",
//        "accountBalance":2000,
//        "email":"ygi@gmail.com",
//         "password":"werty",
//        "phoneNumber":"122344",
//        "alternatePhoneNumber":"12345",
//        "status":"ACTIVE"