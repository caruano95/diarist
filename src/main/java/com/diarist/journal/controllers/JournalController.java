package com.diarist.journal.controllers;


import com.diarist.journal.models.JournalEntry;
import com.diarist.journal.models.JournalService;
import com.diarist.journal.models.User;
import com.diarist.journal.models.UserService;
import com.diarist.journal.util.PhoneUtils;
import com.google.gson.*;
import spark.Route;

import java.util.List;

/**
 * Journal controller class. Holds all the methods that handle journal entries.
 */
public class JournalController {

    private final Gson gson = new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation()
                            .setDateFormat("dd/MMM/yyyy")
                            .create();

    private JournalService journalService;
    private UserService userService;
    private PhoneUtils phoneUtils;

    public JournalController(UserService userService, JournalService journalService) {
        this.userService = userService;
        this.journalService = journalService;
        this.phoneUtils = new PhoneUtils();
    }

    public Route getList = (request, response) -> {
        String username = request.queryParams("username");
        System.out.println( String.format("Getting a list of journal entries for %s", username));

        User user = userService.findByUsername(username);
        List<JournalEntry> journal = journalService.getJournal(user);
        response.type("application/json");
        return gson.toJson(journal);
    };

    public Route create = (request, response) -> {
        JsonObject jsonObject = JsonParser.parseString(request.body()).getAsJsonObject();


        final JsonElement phoneParameter = jsonObject.get("phoneNumber");
        final JsonElement usernameParameter = jsonObject.get("username");

        User user;
        if (phoneParameter != null) {
            final String number = phoneUtils.getFormattedPhoneNumber(phoneParameter.getAsString());
            user = userService.findByPhone(number);
        } else if (usernameParameter != null){
            user = userService.findByUsername(usernameParameter.getAsString());
        } else {
            response.status(400);
            return response;
        }

        final String messageContent = jsonObject.get("content").getAsString();

        JournalEntry journalEntry = new JournalEntry(user, messageContent);
        journalService.save(journalEntry);

        response.status(202);
        return "";
    };

}
