package com.diarist.journal.models;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
    public Long id;

    @Column
    public String username;

    @Column
    public String phoneNumber;

    @Column
    public String passcode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public User() {
    }

    public User(String username, String phoneNumber, String passcode) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.passcode = passcode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
