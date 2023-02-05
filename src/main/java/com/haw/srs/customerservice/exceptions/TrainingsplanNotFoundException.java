package com.haw.srs.customerservice.exceptions;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Value
@EqualsAndHashCode(callSuper=false)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrainingsplanNotFoundException extends Exception {

    private final Long trainingsplanId;

    public TrainingsplanNotFoundException(Long trainingsplanId) {
        super(String.format("Could not find Trainingsplan with number %d.", trainingsplanId));
        this.trainingsplanId = trainingsplanId;
    }
}
