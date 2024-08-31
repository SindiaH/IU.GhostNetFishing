package com.controllers;

import com.beans.AuthenticationBean;
import com.data.GhostnetDataStore;
import com.helper.MessageHelper;
import com.services.Validator;
import entities.Ghostnet;
import com.enums.GhostnetStatus;
import entities.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class ReportGhostnetController {
    private final GhostnetDataStore ghostnetDataStore;

    public ReportGhostnetController() {
        ghostnetDataStore = GhostnetDataStore.getInstance();
    }

    @ManagedProperty(value = "#{authenticationBean}")
    private AuthenticationBean authenticationBean;

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    private String reporterName;
    private String phoneNumber;
    private String longitude;
    private String latitude;
    private int size;

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String reportGhostnet() {
        User user = authenticationBean.getLoggedInUser();

        if (authenticationBean.getIsLoggedIn() && user != null) {
            Ghostnet ghostnet = new Ghostnet(user.Name, user.Telephone,
                    this.longitude, this.latitude, this.size, GhostnetStatus.Reported);
            this.ghostnetDataStore.addGhostnet(ghostnet);
        } else if (Validator.isNullOrEmpty(this.reporterName)) {
            MessageHelper.addErrorMessage("Der Name darf nicht leer sein");
        } else {
            Ghostnet ghostnet = new Ghostnet(this.reporterName, this.phoneNumber, this.longitude, this.latitude,
                    this.size, GhostnetStatus.Reported);
            this.ghostnetDataStore.addGhostnet(ghostnet);
        }

        MessageHelper.addInfoMessage("Erfolg", "Das Ghostnet wurde erfolgreich gemeldet");
        this.ClearForm();
        return "";
    }

    public void validateCoordinateFormat(FacesContext context, UIComponent component, String value) {
        if (Validator.isNullOrEmpty(value)) {
            MessageHelper.throwErrorMessage("Längen- und Breitengrad sind Pflichtfelder");
        } else if (Validator.isInvalidCoordinate(value)) {
            MessageHelper.throwErrorMessage("Längen- und Breitengrad müssen das typische Format haben, zB.: [+-]12.10020");
        }
    }

    private void ClearForm() {
        this.longitude = null;
        this.latitude = null;
        this.size = 0;
    }
}
