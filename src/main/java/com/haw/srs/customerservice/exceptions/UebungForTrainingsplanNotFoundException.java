package com.haw.srs.customerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UebungForTrainingsplanNotFoundException extends Exception {

    private final Long uebungId;

    public UebungForTrainingsplanNotFoundException(Long uebungId) {
        super(String.format("Could not find Übung with number %d.", uebungId));
        this.uebungId = uebungId;
    }
}
