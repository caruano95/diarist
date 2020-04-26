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
    public List<JournalEntry> getJournal(User user) {
        String queryString = "SELECT e FROM JournalEntry e WHERE e.user = :user ORDER BY e.id DESC";
        TypedQuery<JournalEntry> query = entityManager.createQuery(queryString, JournalEntry.class);
        query.setParameter("user", user);
        return query.getResultList();
    }

}
