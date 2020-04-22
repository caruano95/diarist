# diarist
Journaling made simple

# Info
Inspired by the Appointments app example
https://www.twilio.com/docs/sms/tutorials/appointment-reminders-java-spark

# Dependencies
* MySQL 

# Properties

#### Mysql

| Property Name    | Default Value |
| --------------   | ------------- |
| DATABASE_USER    |               |
| DATABASE_PASS    |               |
| DATABASE_HOST    | localhost     |
| DATABASE_PORT    | 3306          |
| DATABASE_SCHEMA  | diarist       |

#### Twilio


| Property Name      | Default Value |
| --------------     | ------------- |
| TWILIO_AUTH_TOKEN  |               |
| TWILIO_ACCOUNT_SID |               |
| TWILIO_NUMBER      |               |


# How to 
Execute tests
```
mvn compile test -Dexec.cleanupDaemonThreads=false
```

Run migrations
```
mvn clean compile exec:java -Dexec.mainClass=com.diarist.journal.Migrator
```

Execute the app
```
mvn clean compile exec:java -Dexec.mainClass=com.diarist.journal.Server -Dexec.cleanupDaemonThreads=false
```

# Instalation guide

1. Configure environment variables for all the properties without defaults (and also those you want to override)
1. Make sure you have an instance of MySQL running
1. Create a schema for the app. Should be called `diarist` unless `DATABASE_SCHEMA` is set
1. Run migrations with the maven command
1. Execute the app 
