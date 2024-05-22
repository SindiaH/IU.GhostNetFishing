package com.controllers;

import com.services.MessageHelper;
import com.services.UserService;
import com.services.Validator;
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
public class RegisterController {
//    @Inject @Named("userService") UserService userService;

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private String username;
    private String name;
    private String password;
    private String confirmPassword;
    private String telephone;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void register() {
        try {
            System.out.println("Trying to create user " + this.username);
            User newUser = new User(this.name, this.username, this.telephone, this.password);
            this.userService.addData(newUser);
            this.userService.login(this.username, this.password);
        } catch (Exception e) {
            MessageHelper.addErrorMessage("Beim Anlegen des Benutzers ist ein Fehler aufgetreten:" + e.getMessage());
        }
    }

    public void validatePassword(FacesContext context, UIComponent component, Object value) {
        if (Validator.isNullOrEmpty(this.password) || Validator.isNullOrEmpty(this.confirmPassword)) {
            MessageHelper.addErrorMessage("Das Passwort ist ein Pflichtfeld", context, component);
        } else if (!this.password.equals(this.confirmPassword)) {
            MessageHelper.addErrorMessage("Die Passwörter stimmen nicht überein!", context, component);
        }
    }

}
