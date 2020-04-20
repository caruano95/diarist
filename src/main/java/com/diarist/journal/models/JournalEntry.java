package com.diarist.journal.models;


import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "journal_entry")
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column
    private String content;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public JournalEntry(String user, String content) {
        this.userId = user;
        this.content = content;
    }

    public JournalEntry() {
    }
}
