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
    
    public int UserId;
    public int AssignedUserId;
    
    public String Longitude;
    public String Latitude;
    public int Size;
    public Date CreatedAt;
    
    public GhostnetStatus Status;
}
