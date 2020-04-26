package com.diarist.journal.controllers;

import com.diarist.journal.models.*;
import spark.Route;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Handles message responses from Whatsapp
 */
public class WhatsappController {

    private UserService userService;
    private JournalService journalService;
    private WhatsappService whatsappService;

    public WhatsappController(UserService userService, JournalService journalService, WhatsappService whatsappService) {
        this.userService = userService;
        this.journalService = journalService;
        this.whatsappService = whatsappService;
    }

    /**
     * Receive and persist a message response from a twilio webhook.
     * For testing it requires ngrok setup:
     * http://8e8bf49f.ngrok.io/api/adapter/whatsapp
     */
    public Route newMessage = (request, response) -> {
        String messageText = URLDecoder.decode(request.queryParams("Body"), StandardCharsets.UTF_8);
        final String senderNumber = whatsappService.getSenderNumber(request.queryParams("From"));
        if (senderNumber != null) {
            User user = userService.findByPhone(senderNumber);
            JournalEntry journalEntry = new JournalEntry(user, messageText);
            journalService.save(journalEntry);
        }
        return "";
    };

}
