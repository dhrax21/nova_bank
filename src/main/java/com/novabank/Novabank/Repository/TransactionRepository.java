package com.novabank.Novabank.Repository;

import com.novabank.Novabank.Entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions,String> {

}
