# Diarist
Journaling made simple. 

How it works:
1. You register your number and get a whatsapp message from the app asking you `How was your day today?`
1. You reply to the text with all your adventures
1. That's it ! Once you feel like reading some of your old entries you can go to the web app or consume from the rest API to get your messages formatted and time-stamped.
It will also continue to send you a text every day so that you don't forget to update it.



## About
This app is based on the [Appointment reminders](https://www.twilio.com/docs/sms/tutorials/appointment-reminders-java-spark) java spark app example. 
It requires a MySQL instance up and running to store all the journal entries. 


I also wrote a document [here](docs/diarist.md) sharing some inspirations for the app, my process for developing it and some ideas bout the future.


## How to 
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

## Instalation guide

1. Configure environment variables for all the properties that don't have a default value.
1. Make sure you have an instance of MySQL running.
1. Create a schema for the app. Should be called `diarist` unless `DATABASE_SCHEMA` is set
1. Run migrations with the maven command
1. Execute the app 

## Available properties

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


#### App settings

| Property Name  | Default Value |
| -------------- | ------------- |
|   APP_PORT     |     4567      |


