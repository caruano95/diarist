package com.diarist.journal;

import com.diarist.journal.controllers.AppointmentController;
import com.diarist.journal.controllers.JournalController;
import com.diarist.journal.controllers.WhatsappController;
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
import spark.Request;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import javax.persistence.EntityManagerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

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
                get("/", journalController.getList);
                get("/:entry", journalController.get);
                post("/", journalController.create);
                delete("/:entry", journalController.delete);
            });
            path("/adapter", () -> {
                post("/whatsapp", whatsappController.newMessage);
            });
        });



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

        /** Injects AppointmentService and Scheduler into the controller. */
        //AppointmentController controller = new AppointmentController(service, scheduler);

        /**
         * Defines all url paths for the application and assigns a controller method for each.
         * If the route renders a page, the templating engine must be specified, and the controller
         * should return the appropriate Route object.
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
