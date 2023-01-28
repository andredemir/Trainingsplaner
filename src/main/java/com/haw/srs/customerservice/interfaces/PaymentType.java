package com.haw.srs.customerservice.interfaces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
public interface PaymentType {
    public void pay();
}
