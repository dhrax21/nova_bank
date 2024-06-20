package com.novabank.Novabank.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor@Builder
@Entity
@Table(name ="transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionID;
    private String transactionType;
    private BigDecimal amount;
    private String accountNumber;

    private String status;

}
