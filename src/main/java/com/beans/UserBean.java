package com.beans;

import com.data.UserDataStore;
import com.helper.MessageHelper;
import com.services.AuthCookieService;
import com.services.Validator;
import dtos.AuthCookieDto;
import entities.User;
import jakarta.inject.Named;

import javax.faces.bean.*;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import java.time.LocalDateTime;

@ManagedBean
@SessionScoped
@Named("userBean")
public class UserBean {
    private final UserDataStore userDataStore;

    public UserBean() {
        userDataStore = UserDataStore.getInstance();
        AuthCookieDto user = AuthCookieService.getUserFromCookie();
        if (isCookieValid(user)) {
            User userFromDb = userDataStore.readData(user.username);
            if (userFromDb != null) {
                setLoggedInUser(userFromDb);
            }
        }
    }

    private boolean isCookieValid(AuthCookieDto user) {
        return user != null && user.validUntil.isAfter(LocalDateTime.now());
    }

    private User LoggedInUser;

    public User getLoggedInUser() {
        return LoggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        AuthCookieService.saveUserToCookie(loggedInUser);
        this.setLoggedInUserName(loggedInUser.Username);
        this.setLoggedInName(loggedInUser.Name);
        this.setLoggedInTelephone(loggedInUser.Telephone);
        this.LoggedInUser = loggedInUser;
    }

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

    public void tryLogin(String username, String password) {
        try {
            User user = userDataStore.readData(username);
            if (user != null && user.isPasswordValid(password)) {
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
        AuthCookieService.deleteAuthCookie();
        this.setLoggedInUserName(null);
        this.setLoggedInName(null);

        this.setLoggedInTelephone(null);
        this.LoggedInUser = null;
        return "true";
    }

    public void validateNotRequiredTelephone(FacesContext context, UIComponent component, String value) {
        if (Validator.isInvalidPhoneNumber(value, false)) {
            MessageHelper.throwErrorMessage("Telefonnummer muss das typische Format haben, zB.: 0123456789");
        }
    }

    public void addData(User user) {
        userDataStore.addData(user);
    }
}
