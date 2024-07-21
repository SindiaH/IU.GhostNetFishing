package entities;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import enums.GhostnetStatus;

import java.util.Date;

@Entity
public class Ghostnet {
    @Id
    @GeneratedValue
    private int id;
    
    public Ghostnet() {}
    public Ghostnet(String reporterName, String phoneNumber, String longitude, String latitude, int size, GhostnetStatus status) {
        this.ReporterName = reporterName;
        this.PhoneNumber = phoneNumber;
        this.Longitude = longitude;
        this.Latitude = latitude;
        this.Size = size;
        this.CreatedAt = new Date();
        this.Status = status;
    }

    public Ghostnet(int userId, String reporterName, String phoneNumber, String longitude, String latitude, int size, GhostnetStatus status) {
        this.ReporterId = userId;
        this.ReporterName = reporterName;
        this.PhoneNumber = phoneNumber;
        this.Longitude = longitude;
        this.Latitude = latitude;
        this.Size = size;
        this.CreatedAt = new Date();
        this.Status = status;
    }
    
    public int ReporterId;
    public String ReporterName;
    public String PhoneNumber;
    public int AssignedUserId;
    
    public String Longitude;
    public String Latitude;
    public int Size;
    public Date CreatedAt;
    
    public GhostnetStatus Status;

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
    }
    
    public String getReporterName() {
        return ReporterName;
    }
    
    public void setReporterName(String reporterName) {
        ReporterName = reporterName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
    
    public String getLongitude() {
        return Longitude;
    }
    
    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
    
    public String getLatitude() {
        return Latitude;
    }
    
    public void setLatitude(String latitude) {
        Latitude = latitude;
    }
    
    public int getSize() {
        return Size;
    }
    
    public void setSize(int size) {
        Size = size;
    }
    
    public Date getCreatedAt() {
        return CreatedAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }
    
    public GhostnetStatus getStatus() {
        return Status;
    }
    
    public void setStatus(GhostnetStatus status) {
        Status = status;
    }
    
    public String changeStatus(GhostnetStatus status) {
        Status = status;
        addMessage("Save", "Data saved");
        return "ghostnetOverview";
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
