package entities;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.enums.GhostnetStatus;

import java.util.Date;

@Entity
public class Ghostnet {
    @Id
    @GeneratedValue
    private int id;

    public Ghostnet() {
    }

    public Ghostnet(String reporterName, String reporterPhoneNumber, String longitude, String latitude, int size, GhostnetStatus status) {
        this.ReporterName = reporterName;
        this.ReporterPhoneNumber = reporterPhoneNumber;
        this.Longitude = longitude;
        this.Latitude = latitude;
        this.Size = size;
        this.CreatedAt = new Date();
        this.Status = status;
    }

    public Ghostnet(int userId, String reporterName, String reporterPhoneNumber, String longitude, String latitude, int size, GhostnetStatus status) {
        this.ReporterId = userId;
        this.ReporterName = reporterName;
        this.ReporterPhoneNumber = reporterPhoneNumber;
        this.Longitude = longitude;
        this.Latitude = latitude;
        this.Size = size;
        this.CreatedAt = new Date();
        this.Status = status;
    }

    public int ReporterId;
    public String ReporterName;
    public String ReporterPhoneNumber;
    public int AssignedUserId;
    public String AssignedUserName;
    public String AssignedUserPhoneNumber;

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

    public String getReporterPhoneNumber() {
        return ReporterPhoneNumber;
    }

    public void setReporterPhoneNumber(String reporterPhoneNumber) {
        ReporterPhoneNumber = reporterPhoneNumber;
    }

    public String getAssignedUserName() {
        return AssignedUserName;
    }

    public void setAssignedUserName(String assignedUserName) {
        AssignedUserName = assignedUserName;
    }

    public String getAssignedUserPhoneNumber() {
        return AssignedUserPhoneNumber;
    }

    public void setAssignedUserPhoneNumber(String assignedUserPhoneNumber) {
        AssignedUserPhoneNumber = assignedUserPhoneNumber;
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

    public String getStatusName() {
        switch (Status) {
            case Reported:
                return "Gemeldet";
            case RecoveryImminent:
                return "Bergung bevorstehend";
            case Recovered:
                return "Geborgen";
            case Lost:
                return "Verschollen";
            default:
                return "Unknown";
        }
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

    public boolean canSetRecoveryImminent() {
        return Status == GhostnetStatus.Reported;
    }

    public boolean canSetRecovered(User user) {
        return Status == GhostnetStatus.RecoveryImminent && user != null && user.getId() == AssignedUserId;
    }

    public boolean canSetLost(User user) {
        return Status == GhostnetStatus.RecoveryImminent && user != null && user.getId() == AssignedUserId;
    }

    public boolean canChangeStatus(User user) {
        return Status != GhostnetStatus.Lost && Status != GhostnetStatus.Recovered &&
                (canSetRecoveryImminent() || canSetRecovered(user) || canSetLost(user));
    }

    public boolean canSetNewStatus(GhostnetStatus newStatus, User user) {
        if (newStatus == GhostnetStatus.RecoveryImminent && !canSetRecoveryImminent()) {
            return false;
        } else if (newStatus == GhostnetStatus.Recovered && !canSetRecovered(user) ) {
            return false;
        } else if (newStatus == GhostnetStatus.Lost && !canSetLost(user)) {
            return false;
        }
        return true;
    }

    public boolean setNewStatusIfAllowed(GhostnetStatus newStatus, User user) {
        if (!canSetNewStatus(newStatus, user)) {
            return false;
        }
        
        if (newStatus == GhostnetStatus.RecoveryImminent) {
            AssignedUserName = user.Name;
            AssignedUserId = user.getId();
            AssignedUserPhoneNumber = user.Telephone;
        }
        Status = newStatus;
        return true;
    }
}
