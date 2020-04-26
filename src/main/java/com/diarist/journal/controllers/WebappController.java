package com.diarist.journal.controllers;

import com.diarist.journal.models.*;
import com.diarist.journal.util.PhoneUtils;
import spark.ModelAndView;
import spark.Route;
import spark.template.mustache.MustacheTemplateEngine;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Webapp controller class.
 */
public class WebappController {

    private UserService userService;
    private JournalService journalService;
    private PhoneUtils phoneUtils;
    private WhatsappService whatsappService;

    final String CURRENT_USER_SESSION_IDENTIFIER = "user";

    public WebappController(UserService userService, JournalService journalService, WhatsappService whatsappService) {
        this.userService = userService;
        this.journalService = journalService;
        this.whatsappService = whatsappService;
        phoneUtils = new PhoneUtils();
    }

    /*
     * Registration
     */
    public Route getStarted = (request, response) -> render("get_started.mustache");

    public Route registerForm = (request, response) -> {
        String username = URLDecoder.decode(request.queryParams("username"), StandardCharsets.UTF_8);
        String inputPhone = request.queryParams("phoneNumber");
        String passcode = URLDecoder.decode(request.queryParams("passcode"), StandardCharsets.UTF_8);

        /**
         * Validate we understand the phone number and format
         */
        if(!phoneUtils.isValidPhoneNumber(inputPhone)){
            return render(Collections.singletonMap("invalidPhone", true), "get_started.mustache");
        }
        String formattedPhone = phoneUtils.getFormattedPhoneNumber(inputPhone);

        User newUser = new User(username, formattedPhone, passcode);
        userService.save(newUser);

        /**
         * TODO: build functionality to send message and schedule
         */
        whatsappService.sendNotification(newUser);

        response.redirect("/welcome");
        return response;
    };

    public Route welcome = (request, response) -> render("welcome.mustache");

    /*
     * Login
     */
    public Route journalLogin = (request, response) -> render("my_diary_login.mustache");

    public Route journalLoginProcess = (request, response) -> {
        String username = URLDecoder.decode(request.queryParams("username"), StandardCharsets.UTF_8);
        String passcode = URLDecoder.decode(request.queryParams("passcode"), StandardCharsets.UTF_8);

        /*
        Checking for valid credentials
         */
        if (!userService.isUserValid(username, passcode)) {
            return render(Collections.singletonMap("badLogin", true), "my_diary_login.mustache");
        }else {
            System.out.println(String.format("\n\n\nLoading the journal for the following user:\nusername: %s\npasscode: %s\n\n\n", username, passcode));
            /*
             * Set session / cookie
             */
            request.session().attribute(CURRENT_USER_SESSION_IDENTIFIER, username);

            response.redirect("/journal");
            return response;
        }
    };


    public Route journalLogout = (request, response) -> {
        request.session().removeAttribute(CURRENT_USER_SESSION_IDENTIFIER);

        response.redirect("/");
        return response;
    };

    /*
     * Journal Controls
     */

    public Route journal = (request, response) -> {

        //Get user id from session / cookie
        String username = request.session().attribute(CURRENT_USER_SESSION_IDENTIFIER);

        if (username == null) {
            response.redirect("/log_in");
            return response;
        }

        System.out.println(String.format("\n\n\n\n\n\n\nReading journal from user: %s\n\n\n\n\n\n\n", username));
        User user = userService.findByUsername(username);
        List<JournalEntry> journal = journalService.getJournal(user);


        Map<String, Object> map = new HashMap<>();
        map.put("journalEntries", journal);
        map.put("hideNavbar", true);
        return render(map, "journal.mustache");
    };

    /*
     * More info
     */

    public Route onboarding = (request, response) -> render("onboarding.mustache");

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
