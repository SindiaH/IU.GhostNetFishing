package com.controllers;

import com.enums.GhostnetStatus;
import com.services.GhostnetService;
import com.services.MessageHelper;
import com.services.UserService;
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

    public void changeStatus(Ghostnet ghostnet, String status) {
        GhostnetStatus newStatus = GhostnetStatus.valueOf(status);
        if (newStatus == GhostnetStatus.RecoveryImminent) {
            ghostnet.AssignedUserName = userService.getloggedInUserName();
            ghostnet.AssignedUserId = userService.LoggedInUser.getId();
            ghostnet.ReporterPhoneNumber = userService.LoggedInUser.Telephone;
        }
        ghostnet.Status = newStatus;

        ghostnetService.updateGhostnet(ghostnet);

        MessageHelper.addInfoMessage("Status geändert", "Status wurde erfolgreich auf " + status + " geändert");
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
            public String getRowKey(Ghostnet ghostnet) {
                return String.valueOf(ghostnet.getId());
            }
        };
    }
}
