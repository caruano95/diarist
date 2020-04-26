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

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    @Column
    @Expose
    public String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @Expose
    public Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    public JournalEntry() {
    }

    public JournalEntry(User user, String content) {
        this.user = user;
        this.content = content;
    }

    /**
     * Used by the template to display the date as 'Apr 19'
     */
    public String getCreatedAsDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd");
        return formatter.format(created);
    }
}
