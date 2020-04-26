package com.diarist.journal.models;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Class that provides an abstraction to an Appointment's entity database access
 */
public class UserService {
    private final EntityManager entityManager;

    public UserService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(User entry) {
        entityManager.getTransaction().begin();
        entityManager.persist(entry);
        entityManager.getTransaction().commit();
    }

    public User findByPhone(String phoneNumber) {
        String queryString = "SELECT u FROM User u WHERE u.phoneNumber = :phone";
        TypedQuery<User> query = entityManager.createQuery(queryString, User.class);
        query.setParameter("phone", phoneNumber);
        return query.getSingleResult();
    }

    public User findByUsername(String username) {
        String queryString = "SELECT u FROM User u WHERE u.username = :username";
        TypedQuery<User> query = entityManager.createQuery(queryString, User.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    public boolean isUserValid(String username, String passcode){
        return !username.equals("error");
    }


}
