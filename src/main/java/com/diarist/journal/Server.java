package com.diarist.journal;

import com.diarist.journal.controllers.JournalController;
import com.diarist.journal.controllers.WebappController;
import com.diarist.journal.controllers.WhatsappController;
import com.diarist.journal.models.JournalService;
import com.diarist.journal.models.UserService;
import com.diarist.journal.models.WhatsappService;
import com.diarist.journal.util.AppSetup;
import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.*;

import javax.persistence.EntityManager;

import static spark.Spark.*;

/**
 * Main application class. The environment is set up here, and all necessary services are run.
 */
public class Server {

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) {
        AppSetup appSetup = new AppSetup();
        port(appSetup.getAppPortNumber());
        Twilio.init(appSetup.getAccountSid(), appSetup.getAuthToken());

        /**
         * Sets the port in which the application will run. Takes the port value from PORT
         * environment variable, if not set, uses default port 4567.
         */
        port(appSetup.getAppPortNumber());

        /**
         * Specifies the directory within resources that will be publicly available when the
         * application is running. Place static web files in this directory (JS, CSS).
         */
        Spark.staticFileLocation("/public");

        /**
         * Gets the entity manager based on environment variable DATABASE_URL and injects it into
         * JournalService which handles DB operations.
         */
        EntityManager entityManager = appSetup.getEntityManagerFactory().createEntityManager();
        UserService userService = new UserService(entityManager);
        JournalService journalService = new JournalService(entityManager);
        WhatsappService whatsappService = new WhatsappService(appSetup.getTwilioPhoneNumber());

        JournalController journalController = new JournalController(userService, journalService);
        WhatsappController whatsappController = new WhatsappController(userService, journalService, whatsappService);

        path("/api", () -> {
            if (appSetup.isApiEnabled()) {
                path("/diary", () -> {
                    get("", journalController.getList);
                    post("", journalController.create);
                });
            }
            path("/adapter", () -> {
                post("/whatsapp", whatsappController.newMessage);
            });
        });

        /**
         * Frontend app
         */
        WebappController webappController = new WebappController(userService, journalService, whatsappService);
        get("/", webappController.onboarding);

        get("/get_started", webappController.getStarted);
        post("/welcome", webappController.registerForm);
        get("/welcome", webappController.welcome);

        get("/log_in", webappController.journalLogin);
        post("/log_in", webappController.journalLoginProcess);
        post("/log_out", webappController.journalLogout);
        get("/journal", webappController.journal);


        get("/about", webappController.about);
    }

}
