package com.haw.srs.customerservice.datatypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// this represents an immutable datatype (no setter)
// we cannot use @Value from lombok since jpa needs a nonargconstructor when deserializing
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PhoneNumber {

    private static final String PHONENUMBER_PATTERN = "^([+]\\d{2,3})(\\s?|-?)(\\d{2,5})(\\s?|-?)(\\d{4,8})$";
    //"^(\\+\\d{2})-(\\d{2,3})-(\\d{4,})$"

    private String countryCode;

    private String subscriberNumber;

    private String restNumber;

    public PhoneNumber(String phoneNumber) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile(PHONENUMBER_PATTERN);
        Matcher matcher = pattern.matcher(phoneNumber);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
        }

        //Noch nicht korrekt
        countryCode = matcher.group(1);
        restNumber = matcher.group(2);
        subscriberNumber = matcher.group(3) + matcher.group(4) + matcher.group(5);
    }

    public static boolean isValid(String phoneNumber) {
        if (phoneNumber == null)
            return false;
        else
            return phoneNumber.matches(PHONENUMBER_PATTERN);
    }

    public static void main(String[] args) {
        PhoneNumber phoneNumber = new PhoneNumber("+49 176 787654");
    }
}
