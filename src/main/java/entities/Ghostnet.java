package entities;

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
    public Ghostnet(String reporterName, String longitude, String latitude, int size, GhostnetStatus status) {
        this.ReporterName = reporterName;
        this.Longitude = longitude;
        this.Latitude = latitude;
        this.Size = size;
        this.CreatedAt = new Date();
        this.Status = status;
    }

    public Ghostnet(int userId, String reporterName, String longitude, String latitude, int size, GhostnetStatus status) {
        this.ReporterId = userId;
        this.ReporterName = reporterName;
        this.Longitude = longitude;
        this.Latitude = latitude;
        this.Size = size;
        this.CreatedAt = new Date();
        this.Status = status;
    }
    
    public int ReporterId;
    public String ReporterName;
    public int AssignedUserId;
    
    public String Longitude;
    public String Latitude;
    public int Size;
    public Date CreatedAt;
    
    public GhostnetStatus Status;
    
    public String getReporterName() {
        return ReporterName;
    }
    
    public void setReporterName(String reporterName) {
        ReporterName = reporterName;
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
}
