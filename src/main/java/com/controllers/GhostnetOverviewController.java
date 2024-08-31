package com.controllers;

import com.beans.AuthenticationBean;
import com.enums.GhostnetStatus;
import com.data.GhostnetDataStore;
import com.helper.MessageHelper;
import entities.Ghostnet;
import jakarta.inject.Named;
import org.primefaces.model.*;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
@Named("ghostnetOverviewController")
public class GhostnetOverviewController implements Serializable {
    private final GhostnetDataStore ghostnetDataStore;

    public GhostnetOverviewController() {
        ghostnetDataStore = GhostnetDataStore.getInstance();
        lazyModel = readDataLazyModel();
    }

    @ManagedProperty(value = "#{authenticationBean}")
    private AuthenticationBean authenticationBean;

    public AuthenticationBean getAuthenticationBean() {
        return authenticationBean;
    }

    public void setAuthenticationBean(AuthenticationBean authenticationBean) {
        this.authenticationBean = authenticationBean;
    }

    private LazyDataModel<Ghostnet> lazyModel;

    public LazyDataModel<Ghostnet> getLazyModel() {
        return lazyModel;
    }

    public String changeStatus(Ghostnet ghostnet, String status) {
        GhostnetStatus newStatus = GhostnetStatus.valueOf(status);
        Ghostnet ghostnetFromDb = ghostnetDataStore.getGhostnet(ghostnet.getId());
        if(ghostnetFromDb != null && ghostnetFromDb.setNewStatusIfAllowed(newStatus, authenticationBean.getLoggedInUser())){
            ghostnetDataStore.updateGhostnet(ghostnetFromDb);
            ghostnet.Status = ghostnetFromDb.Status;
            ghostnet.AssignedUser = ghostnetFromDb.AssignedUser;

            MessageHelper.addInfoMessage("Status geändert", "Status wurde erfolgreich auf " + status + " geändert");
        } else {
            MessageHelper.addErrorMessage("Fehler", "Status konnte nicht geändert werden");
        }
        return "";
    }

    public LazyDataModel<Ghostnet> readDataLazyModel() {
        return new LazyDataModel<Ghostnet>() {
            @Override
            public int count(Map<String, FilterMeta> map) {
                return Long.valueOf(ghostnetDataStore.readDataCount()).intValue();
            }

            @Override
            public List<Ghostnet> load(int first, int pageSize, Map<String, SortMeta> sortInfo, Map<String, FilterMeta> filterBy) {
                return ghostnetDataStore.lazyRead(first, pageSize, sortInfo, filterBy);
            }

            @Override
            public int getRowCount() {
                return Long.valueOf(ghostnetDataStore.readDataCount()).intValue();
            }

            @Override
            public String getRowKey(Ghostnet ghostnet) {
                return String.valueOf(ghostnet.getId());
            }
        };
    }
}
