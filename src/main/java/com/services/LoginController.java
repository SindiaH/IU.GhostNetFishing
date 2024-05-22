package com.services;

import dtos.MessageDto;
import entities.User;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class LoginController {
    
    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void login() {
        try {
            System.out.println("Trying to log in user " + this.username);
            User user = this.userService.readData(this.username);
            if (user != null && user.isPasswordValid(this.password)) {
                AuthCookieService.saveUserToCookie(user);
                this.userService.setLoggedInUser(user);
            } else if(user != null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username oder Passwort sind nicht valide", "Username oder Passwort sind nicht valide"));
                System.out.println("Username oder Passwort sind nicht valide: " + this.username + " " + this.password + ", isvalid: " + user.isPasswordValid(this.password));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Es kann kein Benutzer mit diesem Namen gefunden werden", "Es kann kein Benutzer mit diesem Namen gefunden werden"));
                System.out.println("Es kann kein Benutzer mit diesem Namen gefunden werden: " + this.username + " " + this.password);
            }
        } catch (Exception e) {
            System.out.println("Error while logging in: " + e);
        }
    }
}
