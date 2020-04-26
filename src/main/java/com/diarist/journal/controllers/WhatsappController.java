package com.diarist.journal.controllers;

import com.diarist.journal.models.JournalEntry;
import com.diarist.journal.models.JournalService;
import com.diarist.journal.models.User;
import com.diarist.journal.models.UserService;
import com.diarist.journal.util.PhoneUtils;
import com.twilio.rest.api.v2010.account.Message;
import spark.Route;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Handles message responses from Whatsapp
 */
public class WhatsappController {

    UserService userService;
    JournalService journalService;
    PhoneUtils phoneUtils;


    public WhatsappController(UserService userService, JournalService journalService) {
        this.userService = userService;
        this.journalService = journalService;
        this.phoneUtils = new PhoneUtils();
    }

    /**
     * Receive and persist a message response from a twilio webhook.
     * For testing it requires ngrok setup:
     * http://8e8bf49f.ngrok.io/api/adapter/whatsapp
     */
    public Route newMessage = (request, response) -> {
        String messageText = URLDecoder.decode(request.queryParams("Body"), StandardCharsets.UTF_8);
        String sender = URLDecoder.decode(request.queryParams("From"), StandardCharsets.UTF_8);
        String senderNumber = sender.substring(sender.indexOf(":") + 1);
        senderNumber = phoneUtils.getFormattedPhoneNumber(senderNumber);

        System.out.println("senderNumber = " + senderNumber);

        User user = userService.findByPhone(senderNumber);
        JournalEntry journalEntry = new JournalEntry(user, messageText);
        journalService.save(journalEntry);

        return "";
    };

    //appSetup.getTwilioPhoneNumber()
    public void sendNotification() {
        final String senderPhoneNumber = "+50259781548";
        final String recipientPhoneNumber = "+50259781548";
        final String messageBody = "How was your day today?";

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(String.format("whatsapp:%s", senderPhoneNumber)),
                new com.twilio.type.PhoneNumber(String.format("whatsapp:%s", recipientPhoneNumber)),
                messageBody)
                .create();
        System.out.println(message.getSid());
    }


}
