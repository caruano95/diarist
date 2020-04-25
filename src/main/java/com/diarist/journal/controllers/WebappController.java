package com.diarist.journal.controllers;

import com.diarist.journal.models.JournalEntry;
import com.diarist.journal.models.JournalService;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;
import spark.template.mustache.MustacheTemplateEngine;

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

    public Route onboarding = (request, response) -> render("onboarding.mustache");


    /*
     * Registration
     */
    public Route getStarted = (request, response) -> render("get_started.mustache");

    public Route registerForm = (request, response) -> {
        String inputPhone = URLDecoder.decode(request.queryParams("phoneNumber"), StandardCharsets.UTF_8);
        String optionPrompt = URLDecoder.decode(request.queryParams("optionPrompt"), StandardCharsets.UTF_8);

        System.out.println(String.format("\n\n\nRegistering new user:\ninputPhone: %s\noptionPrompt: %s\n\n\n", inputPhone, optionPrompt));





        response.redirect("/");
        return response;
    };

    /*
     * Login and open journal
     */

    public Route myDiaryLogin = (request, response) -> render( "my_diary_login.mustache");


    public Route journal = (request, response) -> {
        System.out.println("\naskfjnasjfn"+request.queryParams("phoneNumber"));

        Map map = new HashMap();

        String userId = "+12345678";

        List<JournalEntry> journal = journalService.getJournal(userId);


        map.put("journal1", journal);
        return render(map, "journal.mustache");
    };


    /*
     * More info
     */

    public Route about = (request, response) -> render("about.mustache");

    /*
     * Utilities
     */
    public static String render(Map<String, Object> model, String templatePath) {
        return new MustacheTemplateEngine().render(new ModelAndView(model, templatePath));
    }

    public static String render(String templatePath) {
        Map<String, Object> map = new HashMap<>();
        return render(map, templatePath);
    }

}
