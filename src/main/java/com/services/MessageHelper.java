package com.services;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class MessageHelper {

    public static void addErrorMessage(String info){
        MessageHelper.addErrorMessage(info, null, null);
    }
    
    public static void addErrorMessage(String info, FacesContext context, UIComponent component) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, info, info);
        
        if (context!= null && component!= null) {
            context.addMessage(component.getClientId(context), message);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        
        System.out.println(info);
    }
    
}
