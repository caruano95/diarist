package com.diarist.journal.controllers;


import com.diarist.journal.models.JournalEntry;
import com.diarist.journal.models.JournalService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import spark.Route;

import java.util.List;

/**
 * Journal controller class. Holds all the methods that handle journal entries.
 */
public class JournalController {


    private Gson gson = new GsonBuilder()
                            .excludeFieldsWithoutExposeAnnotation()
                            .setDateFormat("dd/MMM/yyyy")
                            .create();

    private JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    public Route getList = (request, response) -> {
        String userId = request.queryParams("user");
        System.out.println( String.format("Getting a list of journal entries for %s", userId));

        List<JournalEntry> journal = journalService.getJournal(userId);
        response.type("application/json");
        return gson.toJson(journal);
    };

    public Route create = (request, response) -> {
        JsonObject jsonObject = JsonParser.parseString(request.body()).getAsJsonObject();

        final String number = jsonObject.get("phoneNumber").getAsString();
        final String messageContent = jsonObject.get("content").getAsString();

        JournalEntry journalEntry = new JournalEntry(number, messageContent);
        journalService.save(journalEntry);

        return "";
    };

}
