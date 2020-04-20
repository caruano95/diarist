package com.diarist.journal.controllers;

import com.diarist.journal.models.Appointment;
import com.diarist.journal.models.AppointmentService;
import com.diarist.journal.models.JournalEntry;
import com.diarist.journal.models.JournalService;
import com.diarist.journal.util.AppointmentScheduler;
import com.diarist.journal.util.FieldValidator;
import com.diarist.journal.util.TimeZones;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import spark.ModelAndView;
import spark.Route;
import spark.TemplateViewRoute;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Webapp controller class.
 */
public class WebappController {

    JournalService journalService;

    public WebappController(JournalService journalService) {
        this.journalService = journalService;
    }

    public TemplateViewRoute journalEntries = (request, response) -> {
        Map map = new HashMap();

        String userId = "+12345678";

        List<JournalEntry> meep = journalService.getJournal(userId);


        map.put("meep", meep);
        return new ModelAndView(map, "index.mustache");
    };


}
