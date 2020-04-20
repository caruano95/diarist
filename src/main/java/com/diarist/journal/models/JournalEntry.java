package com.diarist.journal.models;

import com.google.gson.annotations.Expose;

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
    @Expose
    private String content;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @Expose
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

    @Override
    public String toString() {
        return "JournalEntry{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", created=" + created +
                '}';
    }
}
