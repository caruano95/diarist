package com.diarist.journal.models;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Class that provides an abstraction to an Appointment's entity database access
 */
public class JournalService {
    private final EntityManager entityManager;

    public JournalService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(JournalEntry entry) {
        entityManager.getTransaction().begin();
        entityManager.persist(entry);
        entityManager.getTransaction().commit();
    }

    @SuppressWarnings("unchecked")
    public List<JournalEntry> getJournal(String user_id) {
        String queryString = "SELECT e FROM JournalEntry e WHERE e.userId = :user_id";
        TypedQuery<JournalEntry> query = entityManager.createQuery(queryString, JournalEntry.class);
        query.setParameter("user_id", user_id);
        return query.getResultList();
    }

}
