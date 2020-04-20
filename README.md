# diarist
Journaling made simple

# Info
Inspired by the Appointments app example
https://www.twilio.com/docs/sms/tutorials/appointment-reminders-java-spark

# Dependencies
* MySQL 

# Properties

#### Mysql

* DATABASE_USER
* DATABASE_PASS
* DATABASE_HOST

#### Twilio

* TWILIO_AUTH_TOKEN
* TWILIO_ACCOUNT_SID
* TWILIO_NUMBER

# How to 
Run migrations
```
clean compile exec:java -Dexec.mainClass=com.diarist.journal.Migrator
```

Execute
```
clean compile exec:java -Dexec.mainClass=com.diarist.journal.Server -Dexec.cleanupDaemonThreads=false
```

