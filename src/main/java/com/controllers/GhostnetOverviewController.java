package com.controllers;

import com.services.GhostnetService;
import com.services.MessageHelper;
import com.services.UserService;
import entities.Ghostnet;
import enums.GhostnetStatus;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
@Named("ghostnetOverviewController")
public class GhostnetOverviewController implements Serializable {
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
    
    private LazyDataModel<Ghostnet> lazyModel;
    
    public GhostnetOverviewController() {
        lazyModel = readDataLazyModel();
    }

    public LazyDataModel<Ghostnet> getLazyModel() {
        return lazyModel;
    }

    public String changeStatus(Ghostnet ghostnet, String status) {
        addMessage("Status ändern", "Ändere Status" + status +  " der Id: " + ghostnet.getId());
        return ghostnet.getId() + "";
    }

    public void changeStatus2(int ghostnetId, String status) {
        addMessage("Status ändern 2", "Ändere Status" + status +  " der Id: " + ghostnetId);
    }

    public String test() {
        System.out.println("Logged out: ");
        MessageHelper.addErrorMessage("Fehler", "Der Name des Benutzers ist ein Pflichtfeld");
        return "true";
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void onStatusChosen(SelectEvent event) {
        GhostnetStatus status = (GhostnetStatus) event.getObject();
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Status Selected", "Name:" + status);

        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void selectStatus(SelectEvent event) {
        PrimeFaces.current().dialog().closeDynamic(event);
    }

    public LazyDataModel<Ghostnet> readDataLazyModel() {
        return new LazyDataModel<Ghostnet>() {
            @Override
            public int count(Map<String, FilterMeta> map) {
                return Long.valueOf(ghostnetService.readDataCount()).intValue();
            }

            @Override
            public List<Ghostnet> load(int first, int pageSize, Map<String, SortMeta> sortInfo, Map<String, FilterMeta> filterBy) {
                return ghostnetService.lazyRead(first, pageSize, sortInfo, filterBy);
            }

            @Override
            public int getRowCount() {
                return Long.valueOf(ghostnetService.readDataCount()).intValue();
            }

            @Override
            public String  getRowKey(Ghostnet ghostnet) {
                return String.valueOf(ghostnet.getId());
            }
        };
    }
}
