package com.diarist.journal.controllers;


import com.diarist.journal.models.JournalEntry;
import com.diarist.journal.models.JournalService;
import spark.Route;

/**
 * Journal controller class. Holds all the methods that handle journal entries.
 */
public class JournalController {

    JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    public Route getList = (request, response) -> {
        System.out.println( "Getting a list of journal entries" );

        return "";
    };


    public Route get = (request, response) -> {
        String entry = request.queryParams("entry");
        System.out.println( String.format("Obtaining Journal entry: %s", entry) );

        return "";
    };


    public Route create = (request, response) -> {
        System.out.println("Creating a new journal entry");

        /*
            TODO: Get this two from the request data
         */
        String number = "53940625";
        String messageContent = "Hi there, this is a new Journal Entry from the API";

        JournalEntry journalEntry = new JournalEntry(number, messageContent);

        journalService.save(journalEntry);

        return "";
    };


    public Route delete = (request, response) -> {
        String entry = request.queryParams("entry");
        System.out.println( String.format("Deleting a Journal entry: %s", entry) );

        return "";
    };


}
