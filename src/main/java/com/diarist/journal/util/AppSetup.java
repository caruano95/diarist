package com.diarist.journal.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that holds methods to obtain configuration parameters from the environment.
 */
public class AppSetup {
    private final Map<String, String> env;

    public AppSetup() {
        this.env = System.getenv();
    }

    /**
     * Returns an entity manager factory using the defined environment variables that this class
     * has access to.
     *
     * @return EntityManagerFactory
     */
    public EntityManagerFactory getEntityManagerFactory() {
        AppSetup appSetup = new AppSetup();

        Map<String, String> configOverrides = new HashMap<>();
        configOverrides.put("javax.persistence.jdbc.url", appSetup.getDatabaseUrl());
        configOverrides.put("javax.persistence.jdbc.user", appSetup.getDatabaseUser());
        configOverrides.put("javax.persistence.jdbc.password", appSetup.getDatabasePass());

        return Persistence.createEntityManagerFactory("DiaristPersistence", configOverrides);
    }

    public String getDatabaseUrl(){
        return String.format("jdbc:mysql://%s:%s/%s", getDatabaseHost(), getDatabasePort(), getDatabaseSchema());
    }

    public String getDatabaseHost() {
        final String host = env.get("DATABASE_HOST");
        return host != null ? host : "localhost";
    }

    public int getDatabasePort() {
        String port = env.get("DATABASE_PORT");
        return port != null ? Integer.parseInt(port) : 3306;
    }

    public String getDatabaseSchema() {
        final String schema = env.get("DATABASE_SCHEMA");
        return schema != null ? schema : "diarist";
    }

    public String getDatabaseUser() {
        return env.get("DATABASE_USER");
    }

    public String getDatabasePass() {
        return env.get("DATABASE_PASS");
    }

    public String getAccountSid() {
        return env.get("TWILIO_ACCOUNT_SID");
    }

    public String getAuthToken() {
        return env.get("TWILIO_AUTH_TOKEN");
    }

    public String getTwilioPhoneNumber() {
        return env.get("TWILIO_NUMBER");
    }

    public int getAppPortNumber() {
        String port = env.get("APP_PORT");
        return port != null ? Integer.parseInt(port) : 4567;
    }

    public boolean isApiEnabled() {
        return Boolean.parseBoolean(env.get("ENABLE_API"));
    }
}
