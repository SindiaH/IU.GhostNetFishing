package com.services;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public class MessageHelper {

    public static void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public static void addErrorMessage(String summary, String detail) {
        addMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
    }

    public static void addErrorMessage(String summary) {
        addMessage(FacesMessage.SEVERITY_ERROR, "Error", summary);
    }

    public static void addInfoMessage(String summary, String detail) {
        addMessage(FacesMessage.SEVERITY_INFO, summary, detail);
    }

    public static void throwErrorMessage(String info) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehler", info);

        throw new ValidatorException(message);
    }

}
