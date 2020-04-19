package com.diarist.journal;

import com.diarist.journal.util.AppSetup;
import org.flywaydb.core.Flyway;

/**
 * Helper class used to run database migrations. This must be executed before the actual
 * application. For testing you must specify a different DATABASE_URL environment variable pointing
 * to your testing database.
 */
class Migrator {
    public static void main(String[] args) throws Exception {
        AppSetup appSetup = new AppSetup();
        /**
         * Uses Flyway to run database migrations. Migration files are located in
         * resources/db/migration directory.
         */
        Flyway flyway = Flyway.configure().dataSource(appSetup.getDatabaseUrl(), appSetup.getDatabaseUser(), appSetup.getDatabasePass()).load();
        flyway.migrate();
    }
}
