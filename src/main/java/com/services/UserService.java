package com.services;

import dtos.AuthCookieDto;
import entities.User;
import jakarta.inject.Named;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@ManagedBean
@SessionScoped
@Named("userService")
public class UserService {
    EntityManager entityManager;

    public UserService() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("ghost-net-app");
        entityManager = factory.createEntityManager();
        System.out.println("Starting UserService...");
        AuthCookieDto user = AuthCookieService.getUserFromCookie();
        if (isCookieValid(user)) {
            setLoggedInUser(new User(user.name, user.username, user.telephone));
        }
    }

    private boolean isCookieValid(AuthCookieDto user) {
        return user != null && user.validUntil.isAfter(LocalDateTime.now());
    }

    public User LoggedInUser;

    private String loggedInUserName;

    public String getloggedInUserName() {
        return loggedInUserName;
    }

    public void setLoggedInUserName(String loggedInUserName) {
        this.loggedInUserName = loggedInUserName;
    }

    private String loggedInName;

    public String getLoggedInName() {
        return loggedInName;
    }

    public void setLoggedInName(String loggedInName) {
        this.loggedInName = loggedInName;
    }

    private String loggedInTelephone;

    public String getLoggedInTelephone() {
        return loggedInTelephone;
    }

    public void setLoggedInTelephone(String loggedInTelephone) {
        this.loggedInTelephone = loggedInTelephone;
    }

    private boolean isLoggedIn;

    public boolean getIsLoggedIn() {
        return !Validator.isNullOrEmpty(this.loggedInName);
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public void setLoggedInUser(User user) {
        System.out.println("Logged in: " + user.Username);
        AuthCookieService.saveUserToCookie(user);
        this.setLoggedInUserName(user.Username);
        this.setLoggedInName(user.Name);
        this.setLoggedInTelephone(user.Telephone);
        this.LoggedInUser = user;
    }

    public void login(String username, String password) {
        try {
            System.out.println("Trying to log in user " + username);
            User user = this.readData(username);
            if (user != null && user.isPasswordValid(password)) {
                System.out.println("Settings user " + username);
                this.setLoggedInUser(user);
                FacesContext.getCurrentInstance().getExternalContext().redirect("/report");
            } else if (user != null) {
                MessageHelper.addErrorMessage("Username oder Passwort sind nicht valide");
            } else {
                MessageHelper.addErrorMessage("Es kann kein Benutzer mit diesem Namen gefunden werden: " + username);
            }
        } catch (Exception e) {
            MessageHelper.addErrorMessage("Beim Login ist ein Fehler aufgetreten: " + e);
        }
    }

    public String logout() {
        System.out.println("Logged out: " + this.loggedInName);
        AuthCookieService.deleteAuthCookie();
        this.setLoggedInUserName(null);
        this.setLoggedInName(null);
        this.setLoggedInTelephone(null);
        this.LoggedInUser = null;
        return "true";
    }

    public void addData(User user) {
        if (!validateUser(user)) {
            return;
        }
        if(userExists(user)) {
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

    private boolean userExists(User user) {
        String jpql = "SELECT p from User p WHERE p.Username = :username";
        Query query = this.entityManager.createQuery(jpql);
        query.setParameter("username", user.Username);
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
//        System.out.println("Trying to read data for user " + username);
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
