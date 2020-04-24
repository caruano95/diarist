package com.diarist.journal.controllers;

import com.diarist.journal.models.JournalEntry;
import com.diarist.journal.models.JournalService;
import spark.ModelAndView;
import spark.TemplateViewRoute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Webapp controller class.
 */
public class WebappController {

    JournalService journalService;

    public WebappController(JournalService journalService) {
        this.journalService = journalService;
    }

    public TemplateViewRoute index = (request, response) -> {
        Map map = new HashMap();

        return new ModelAndView(map, "journal.mustache");
    };

    public TemplateViewRoute getStarted = (request, response) -> {
        Map map = new HashMap();

        return new ModelAndView(map, "journal.mustache");
    };


    public TemplateViewRoute myDiary = (request, response) -> {
        Map map = new HashMap();

        String userId = "+12345678";

        List<JournalEntry> journal = journalService.getJournal(userId);


        map.put("journal1", journal);
        return new ModelAndView(map, "journal.mustache");
    };

}
