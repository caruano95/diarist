package com.diarist.journal.models;

import com.diarist.journal.util.PhoneUtils;
import com.twilio.rest.api.v2010.account.Message;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

/**
 * Class that provides an abstraction to an Appointment's entity database access
 */
public class WhatsappService {

    private PhoneUtils phoneUtils;
    private String twilioPhoneNumber;

    public WhatsappService(String twilioPhoneNumber) {
        this.twilioPhoneNumber = twilioPhoneNumber;
        this.phoneUtils = new PhoneUtils();
    }

    public String getSenderNumber(String formField){
        String senderNumber = formField.substring(formField.indexOf(":") + 1);
        boolean phoneNumberIsValid = phoneUtils.isValidPhoneNumber(senderNumber);
        if (phoneNumberIsValid) {
            String formattedPhoneNUmber = phoneUtils.getFormattedPhoneNumber(senderNumber);
            System.out.println(String.format("Validating the phone number %s, result isValid=%b", formattedPhoneNUmber, phoneNumberIsValid));
            return formattedPhoneNUmber;
        } else {
            return null;
        }
    }

    public void sendNotification(User user) {
        final String messageBody = "How was your day today?";
        System.out.println(String.format("Sending a whatsapp message:\nto: %s\nfrom: %s\ncontent: %s\n\n\n", user.getPhoneNumber(), twilioPhoneNumber, messageBody));
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(String.format("whatsapp:%s", user.getPhoneNumber())),
                new com.twilio.type.PhoneNumber(String.format("whatsapp:%s", twilioPhoneNumber)),
                messageBody)
                .create();
        System.out.println(message.getSid());
    }

}
