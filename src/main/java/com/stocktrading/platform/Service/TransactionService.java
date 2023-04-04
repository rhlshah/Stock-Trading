package com.stocktrading.platform.Service;

import com.stocktrading.platform.entity.Transaction;
import com.stocktrading.platform.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository)
    {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> findAllTransactions(String emailId)
    {
        return transactionRepository.findByEmailId(emailId);
    }



}
