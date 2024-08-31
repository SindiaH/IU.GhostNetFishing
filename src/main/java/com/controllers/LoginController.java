package com.controllers;

import com.beans.AuthenticationBean;
import com.helper.MessageHelper;
import org.h2.expression.Variable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean
@ViewScoped
public class LoginController {

    @ManagedProperty(value = "#{authenticationBean}")
    private AuthenticationBean authenticationBean;

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
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
            boolean loggedIn = this.authenticationBean.tryLogin(this.username, this.password);
            if (loggedIn) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/report");
            }

        } catch (IOException e) {
            MessageHelper.addErrorMessage("Beim Anmelden des Benutzers ist ein Fehler aufgetreten:" + e.getMessage());
        }
    }
}
