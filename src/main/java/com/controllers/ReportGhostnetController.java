package com.controllers;

import com.services.GhostnetService;
import com.services.MessageHelper;
import com.services.UserService;
import com.services.Validator;
import entities.Ghostnet;
import entities.User;
import enums.GhostnetStatus;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class ReportGhostnetController {
    @ManagedProperty(value = "#{ghostnetService}")
    private GhostnetService ghostnetService;

    public GhostnetService getGhostnetService() {
        return ghostnetService;
    }

    public void setGhostnetService(GhostnetService ghostnetService) {
        this.ghostnetService = ghostnetService;
    }

    @ManagedProperty(value = "#{userService}")
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private String reporterName;
    private String longitude;
    private String latitude;
    private int size;

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void reportGhostnet() {
        if (userService.getIsLoggedIn() && userService.LoggedInUser != null) {
            Ghostnet ghostnet = new Ghostnet(userService.LoggedInUser.getId(), userService.LoggedInUser.Name, this.longitude, this.latitude, this.size, GhostnetStatus.Reported);
            this.ghostnetService.addGhostnet(ghostnet);
        } else if (Validator.isNullOrEmpty(this.reporterName)) {
            MessageHelper.addErrorMessage("Der Name darf nicht leer sein");
        } else {
            Ghostnet ghostnet = new Ghostnet(this.reporterName, this.longitude, this.latitude, this.size, GhostnetStatus.Reported);
            this.ghostnetService.addGhostnet(ghostnet);
        }
        
        // TODO: Info about success, clear form

    }
}
