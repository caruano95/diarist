package com.diarist.journal.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL;

public class PhoneUtils {

    PhoneNumberUtil phoneUtil;

    final String DEFAULT_REGION = "US";

    public PhoneUtils(){
        phoneUtil = PhoneNumberUtil.getInstance();
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        PhoneNumber phone = null;
        try {
            phone = phoneUtil.parse(phoneNumber, DEFAULT_REGION);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return phoneUtil.isValidNumber(phone);
    }

    public String getFormattedPhoneNumber(String phoneNumber) {
        PhoneNumber phone = null;
        try {
            phone = phoneUtil.parse(phoneNumber, DEFAULT_REGION);
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        return phoneUtil.format(phone, INTERNATIONAL);
    }
}
