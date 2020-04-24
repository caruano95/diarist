package com.diarist.journal.models;

import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "journal_entry")
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column
    public Long id;

    @Column(name = "user_id")
    public String userId;

    @Column
    @Expose
    public String content;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @Expose
    public Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public JournalEntry() {
    }

    public JournalEntry(String user, String content) {
        this.userId = user;
        this.content = content;
    }

    public String getCreatedAsDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
        return formatter.format(created);
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
