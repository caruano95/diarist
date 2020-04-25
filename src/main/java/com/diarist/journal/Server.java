package com.diarist.journal;

import com.diarist.journal.controllers.AppointmentController;
import com.diarist.journal.controllers.JournalController;
import com.diarist.journal.controllers.WebappController;
import com.diarist.journal.controllers.WhatsappController;
import com.diarist.journal.models.Appointment;
import com.diarist.journal.models.AppointmentService;
import com.diarist.journal.models.JournalService;
import com.diarist.journal.util.AppSetup;
import com.diarist.journal.util.AppointmentScheduler;
import com.diarist.journal.util.LoggingFilter;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.*;
import spark.template.mustache.MustacheTemplateEngine;

import javax.persistence.EntityManagerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        EntityManagerFactory factory = appSetup.getEntityManagerFactory();
        JournalService journalService = new JournalService(factory.createEntityManager());

        JournalController journalController = new JournalController(journalService);
        WhatsappController whatsappController = new WhatsappController(journalService);

        path("/api", () -> {
            path("/diary", () -> {
                get("", journalController.getList);
                post("", journalController.create);
            });
            path("/adapter", () -> {
                post("/whatsapp", whatsappController.newMessage);
            });
        });


        /**
         * Frontend app
         */

        WebappController webappController = new WebappController(journalService);
        get("/", webappController.myDiary, new MustacheTemplateEngine());
        get("/get_started", webappController.getStarted, new MustacheTemplateEngine());
        get("/my_diary", webappController.myDiary, new MustacheTemplateEngine());
        get("/journal", webappController.journal, new MustacheTemplateEngine());
        get("/about", webappController.about, new MustacheTemplateEngine());
        post("/registerForm", webappController.registerForm);
    }

    public static void sendWhatsappMessage(){
        /**
         * Send
         */
        /*
        final String recipientPhoneNumber = "+50259781548";
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + recipientPhoneNumber),
                new com.twilio.type.PhoneNumber("whatsapp:" + appSetup.getTwilioPhoneNumber()),
                "How was your day today?")
                .create();
        System.out.println(message.getSid());
        */
    }

    public static void startServer() {


        /** Creates a new instance of Quartz Scheduler and starts it. */
        /*
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();

        } catch (SchedulerException se) {
            System.out.println("Unable to start scheduler service");
        }
        */

        /*
        get("/", controller.index, new MustacheTemplateEngine());
        get("/new", controller.renderCreatePage, new MustacheTemplateEngine());
        post("/create", controller.create, new MustacheTemplateEngine());
        post("/delete", controller.delete);

        afterAfter(new LoggingFilter());
        */
    }

}
