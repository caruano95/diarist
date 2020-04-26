package com.diarist.journal.models;

import com.diarist.journal.util.PhoneUtils;
import com.twilio.rest.api.v2010.account.Message;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
        String sender = URLDecoder.decode(formField, StandardCharsets.UTF_8);
        String senderNumber = sender.substring(sender.indexOf(":") + 1);
        return phoneUtils.getFormattedPhoneNumber(senderNumber);
    }

    public void sendNotification(User user) {
        final String messageBody = "How was your day today?";
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(String.format("whatsapp:%s", twilioPhoneNumber)),
                new com.twilio.type.PhoneNumber(String.format("whatsapp:%s", user.getPhoneNumber())),
                messageBody)
                .create();
        System.out.println(message.getSid());
    }

}
