package com.diarist.journal.controllers;

import com.diarist.journal.models.JournalEntry;
import com.diarist.journal.models.JournalService;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import spark.ModelAndView;
import spark.Route;
import spark.template.mustache.MustacheTemplateEngine;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL;

/**
 * Webapp controller class.
 */
public class WebappController {

    JournalService journalService;

    final String CURRENT_USER = "user";

    public WebappController(JournalService journalService) {
        this.journalService = journalService;
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
         * Validate we understand the phone number
         */
        isValidPhoneNumber(inputPhone);


        System.out.println(String.format("\n\n\nRegistering new user:\nusername: %s\ninputPhone: %s\npasscode: %s\n\n\n", username, inputPhone, passcode));

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
        if (username.equals("error")) {
            return render(Collections.singletonMap("badLogin", true), "my_diary_login.mustache");
        }else {
            System.out.println(String.format("\n\n\nLoading the journal for the following user:\nusername: %s\npasscode: %s\n\n\n", username, passcode));
            /*
             * Set session / cookie
             */
            request.session().attribute(CURRENT_USER, username);

            response.redirect("/journal");
            return response;
        }
    };


    public Route journalLogout = (request, response) -> {
        request.session().removeAttribute(CURRENT_USER);

        response.redirect("/");
        return response;
    };

    /*
     * Journal Controls
     */

    public Route journal = (request, response) -> {

        String username = request.session().attribute(CURRENT_USER);

        if (username == null) {
            response.redirect("/log_in");
            return response;
        }

        System.out.println(String.format("\n\n\n\n\n\n\nReading journal from user: %s\n\n\n\n\n\n\n", username));


        //Get user id from session / cookie
        String userId = "+12345678";
        Map<String, Object> map = new HashMap<>();
        List<JournalEntry> journal = journalService.getJournal(userId);


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

    public boolean isValidPhoneNumber(String phoneNumber) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        PhoneNumber phone = null;
        try {
            phone = phoneUtil.parse(phoneNumber, "US");
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
        //String formattedPhoneNumber = phoneUtil.format(phone, INTERNATIONAL);
        //System.out.println("formattedPhoneNumber = " + formattedPhoneNumber);
        return phoneUtil.isValidNumber(phone);
    }
}
