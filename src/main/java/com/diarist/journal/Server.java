package com.diarist.journal;

import com.diarist.journal.controllers.AppointmentController;
import com.diarist.journal.models.AppointmentService;
import com.diarist.journal.util.AppSetup;
import com.diarist.journal.util.LoggingFilter;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
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

    public static void main(String[] args) {
        //startServer();

        sendWhatsappMessage();

    }

    public static void sendWhatsappMessage(){

        AppSetup appSetup = new AppSetup();


        Twilio.init(appSetup.getAccountSid(), appSetup.getAuthToken());

        final String recipientPhoneNumber = "+50259781548";


        /**
         * Send
         */

        /*
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:" + recipientPhoneNumber),
                new com.twilio.type.PhoneNumber("whatsapp:" + appSetup.getTwilioPhoneNumber()),
                "How was your day today?")
                .create();

        System.out.println(message.getSid());
        */


        /**
         * Receive (through a webhook to ngrok)
         * http://8e8bf49f.ngrok.io/new_entry
         */



        get("/new_entry", (req, res) -> "Hello there, this reads entries");


        post("/new_entry", (req, res) -> {
            String messageText = URLDecoder.decode(req.queryParams("Body"), StandardCharsets.UTF_8);
            String sender = URLDecoder.decode(req.queryParams("From"), StandardCharsets.UTF_8);
            String senderNumber = sender.substring(sender.indexOf(":") + 1);

            System.out.println(String.format("\n\n Processing a Message from %s:\n%s", senderNumber, messageText));
            return "";
        });

    }


    //public static void main(String[] args) {
    public static void startServer() {
        AppSetup appSetup = new AppSetup();

        /**
         * Sets the port in which the application will run. Takes the port value from PORT
         * environment variable, if not set, uses Spark default port 4567.
         */
        port(appSetup.getPortNumber());

        /**
         * Gets the entity manager based on environment variable DATABASE_URL and injects it into
         * AppointmentService which handles all DB operations.
         */
        EntityManagerFactory factory = appSetup.getEntityManagerFactory();
        AppointmentService service = new AppointmentService(factory.createEntityManager());

        /**
         * Specifies the directory within resources that will be publicly available when the
         * application is running. Place static web files in this directory (JS, CSS).
         */
        Spark.staticFileLocation("/public");

        /** Creates a new instance of Quartz Scheduler and starts it. */
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            scheduler.start();

        } catch (SchedulerException se) {
            System.out.println("Unable to start scheduler service");
        }

        /** Injects AppointmentService and Scheduler into the controller. */
        AppointmentController controller = new AppointmentController(service, scheduler);

        /**
         * Defines all url paths for the application and assigns a controller method for each.
         * If the route renders a page, the templating engine must be specified, and the controller
         * should return the appropriate Route object.
         */
        get("/", controller.index, new MustacheTemplateEngine());
        get("/new", controller.renderCreatePage, new MustacheTemplateEngine());
        post("/create", controller.create, new MustacheTemplateEngine());
        post("/delete", controller.delete);

        afterAfter(new LoggingFilter());
    }
}
