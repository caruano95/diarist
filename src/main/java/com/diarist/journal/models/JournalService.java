package com.diarist.journal.models;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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

}
