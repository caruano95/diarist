package com.diarist.journal.controllers;

import com.diarist.journal.models.JournalEntry;
import com.diarist.journal.models.JournalService;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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

    public TemplateViewRoute about = (request, response) -> {
        Map map = new HashMap();
        return new ModelAndView(map, "about.mustache");
    };

    public TemplateViewRoute getStarted = (request, response) -> {
        Map map = new HashMap();

        return new ModelAndView(map, "get_started.mustache");
    };


    public TemplateViewRoute myDiary = (request, response) -> {
        Map map = new HashMap();

        return new ModelAndView(map, "my_diary.mustache");
    };


    public TemplateViewRoute journal = (request, response) -> {
        System.out.println("\naskfjnasjfn"+request.queryParams("phoneNumber"));

        Map map = new HashMap();

        String userId = "+12345678";

        List<JournalEntry> journal = journalService.getJournal(userId);


        map.put("journal1", journal);
        return new ModelAndView(map, "journal.mustache");
    };

    public Route registerForm = (request, response) -> {
        String inputPhone = URLDecoder.decode(request.queryParams("inputPhone"), StandardCharsets.UTF_8);
        String optionPrompt = URLDecoder.decode(request.queryParams("optionPrompt"), StandardCharsets.UTF_8);

        System.out.println(String.format("\n\n\nRegistering new user:\ninputPhone: %s\noptionPrompt: %s\n\n\n", inputPhone, optionPrompt));





        response.redirect("/");
        return response;
    };




}
