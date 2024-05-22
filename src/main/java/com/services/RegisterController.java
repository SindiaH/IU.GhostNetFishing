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

    private String username = "test";
    private String name = "aki";
    private String password;
    private String confirmPassword;
    private String telephone = "1234";
    private MessageDto message;

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

    public MessageDto getMessage() {
        return message;
    }

    public void setMessage(MessageDto message) {
        this.message = message;
    }

    public void register() {
        try {
            System.out.println("Trying to create user " + this.username);
            User newUser = new User(this.username, this.name, this.password, this.telephone);
            this.userService.addData(newUser);
            User user = this.userService.readData(newUser.Username, newUser.Password);
            if (user != null) {
                AuthCookieService.saveUserToCookie(user);
                this.userService.setLoggedInUser(user);
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username oder Passwort sind nicht valide", "Username oder Passwort sind nicht valide"));
                System.out.println("Username oder Passwort sind nicht valide: " + this.username + " " + this.password + ", isvalid: " + user.isPasswordValid(this.password));
            }
            setMessage(new MessageDto("Benutzer erfolgreich angelegt!", "Benutzer erfolgreich angelegt!", true));
        } catch (Exception e) {
            System.out.println("Error while creating user: " + e);
            setMessage(new MessageDto("Error", e.getMessage(), false));
        }
    }

    public void validatePassword(FacesContext context, UIComponent component, Object value) {
        setMessage(new MessageDto("Info", "Validating pw!", true));
        if (Validator.isNullOrEmpty(this.password) || Validator.isNullOrEmpty(this.confirmPassword)) {
            setMessage(new MessageDto("Das Passwort ist ein Pflichtfeld", "Validating pw!", true));
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Das Passwort ist ein Pflichtfeld", "Das Passwort ist ein Pflichtfeld");
            context.addMessage(component.getClientId(context), message);
        } else if (!this.password.equals(this.confirmPassword)) {
            setMessage(new MessageDto("Die Passwörter stimmen nicht überein!", "Validating pw!", true));
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Die Passwörter stimmen nicht überein!", "Die Passwörter stimmen nicht überein!");
            context.addMessage(component.getClientId(context), message);
        }
    }

}
