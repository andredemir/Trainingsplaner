package com.haw.srs.customerservice.services;

import com.haw.srs.customerservice.interfaces.PaymentType;

public class CreditCardPayment implements PaymentType {
    @Override
    public void pay() {
        //TODO implement payment method
        System.out.println("Payed with credit card");
    }
}
