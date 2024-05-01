package com.example.Payment.service;

import com.example.Payment.entity.Completed_Payments;
import com.example.Payment.repository.CompletedPaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompletedPaymentsImpl implements CompletedPayments{

    @Autowired
    PaymentClient paymentClient;

    @Autowired
    CompletedPaymentsRepository completedPaymentsRepository;
    @Override
    public void saveCompletedPayment(Completed_Payments completedPayment) {
        completedPaymentsRepository.save(completedPayment);
    }
}
