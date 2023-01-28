package com.haw.srs.customerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BillNotFoundException extends Exception {

    private final Long billId;

    public BillNotFoundException(Long billId) {
        super(String.format("Could not find bill with number %d.", billId));
        this.billId = billId;
    }
}
