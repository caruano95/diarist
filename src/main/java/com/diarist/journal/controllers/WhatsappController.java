package com.diarist.journal.controllers;

import com.diarist.journal.models.JournalEntry;
import com.diarist.journal.models.JournalService;
import com.diarist.journal.models.User;
import com.diarist.journal.models.UserService;
import spark.Route;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Handles message responses from Whatsapp
 */
public class WhatsappController {

    UserService userService;
    JournalService journalService;


    public WhatsappController(UserService userService, JournalService journalService) {
        this.userService = userService;
        this.journalService = journalService;
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

        System.out.println("senderNumber = " + senderNumber);

        User user = userService.findByPhone(senderNumber);
        JournalEntry journalEntry = new JournalEntry(user, messageText);
        journalService.save(journalEntry);

        return "";
    };


}
