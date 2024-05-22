package com.controllers;

import com.services.GhostnetService;
import com.services.UserService;
import entities.Ghostnet;
import entities.Pojo;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@ManagedBean
@ViewScoped
public class GhostnetOverviewController {
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
    
    public LazyDataModel<Ghostnet> getLazyModel() { return readDataLazyModel();}
    
    public LazyDataModel<Ghostnet> readDataLazyModel() {
        return new LazyDataModel<Ghostnet>() {
            @Override
            public int getRowCount() {
                return Long.valueOf(ghostnetService.readDataCount()).intValue();
            }

            @Override
            public List<Ghostnet> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, FilterMeta> filterBy) {
                return ghostnetService.lazyRead(first, pageSize, sortField, sortOrder, filterBy);
            }
        };
    }
}
