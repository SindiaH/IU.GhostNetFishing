package com.data;

import com.helper.MessageHelper;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserDataStore {
    EntityManager entityManager;
    private static UserDataStore instance;

    private UserDataStore() {
        entityManager = BaseDataStore.getEntityManager();
    }
    
    public static UserDataStore getInstance() {
        if (instance == null) {
            instance = new UserDataStore();
        }
        return instance;
    }

    public void addData(User user) {
        if (!validateUser(user)) {
            return;
        }
        if (doesUserExist(user)) {
            MessageHelper.addErrorMessage("Fehler", "Ein Benutzer mit diesem Usernamen existiert bereits");
            return;
        }

        this.entityManager.getTransaction().begin();
        this.entityManager.persist(user);
        this.entityManager.getTransaction().commit();
    }

    public void updateData(User user) {
        if (!validateUser(user)) {
            return;
        }
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(user);
        this.entityManager.getTransaction().commit();
    }

    private boolean validateUser(User user) {
        if (user.Name == null || user.Name.isEmpty()) {
            MessageHelper.addErrorMessage("Fehler", "Der Name des Benutzers ist ein Pflichtfeld");
            return false;
        }
        if (user.Username == null || user.Username.isEmpty()) {
            MessageHelper.addErrorMessage("Fehler", "Der Username des Benutzers ist ein Pflichtfeld");
            return false;
        }
        if (user.Password == null || user.Password.isEmpty()) {
            MessageHelper.addErrorMessage("Fehler", "Das Passwort des Benutzers ist ein Pflichtfeld");
            return false;
        }
        if (user.Telephone == null || user.Telephone.isEmpty()) {
            MessageHelper.addErrorMessage("Fehler", "Die Telefonnummer des Benutzers ist ein Pflichtfeld");
            return false;
        }
        return true;
    }

    private boolean doesUserExist(User user) {
        return doesUserExist(user.Username);
    }

    public boolean doesUserExist(String userName) {
        String jpql = "SELECT p from User p WHERE p.Username = :username";
        Query query = this.entityManager.createQuery(jpql);
        query.setParameter("username", userName);
        List<User> result = (List<User>) query.getResultList();
        if (result.size() == 0) {
            return false;
        }
        return true;
    }

    public User readData(int id) {
        String jpql = "SELECT p from User p WHERE p.id = :id";
        Query query = this.entityManager.createQuery(jpql);
        query.setParameter("id", id);
        List<User> result = (List<User>) query.getResultList();
        if (result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    public User readData(String username) {
        String jpql = "SELECT p from User p WHERE p.Username = :username";
        Query query = this.entityManager.createQuery(jpql);
        query.setParameter("username", username);
        List<User> result = (List<User>) query.getResultList();
        if (result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    public User readData(String username, String password) {
        String jpql = "SELECT p from User p WHERE p.Username = :username AND p.Password = :password";
        Query query = this.entityManager.createQuery(jpql);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<User> result = (List<User>) query.getResultList();
        if (result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    public List<User> readData(int first, int maxCount) {
        String jpql = "SELECT p from User p";
        Query query = this.entityManager.createQuery(jpql);
        query.setFirstResult(first).setMaxResults(maxCount);
        List<User> result = (List<User>) query.getResultList();
        return result;
    }
}
