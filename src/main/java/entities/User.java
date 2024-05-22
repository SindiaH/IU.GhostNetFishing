package entities;

import com.services.PasswordHasher;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue
    private int id;
    
    public User() {}

    public User(String name, String username, String telephone, String password) {
        this.Name = name;
        this.Username = username;
        this.setPassword(password);
        this.Telephone = telephone;
    }

    public User(String name, String username, String telephone) {
        this.Name = name;
        this.Username = username;
        this.Telephone = telephone;
    }
    
    public String Name;
    public String Username;
    public String Password;
    public String Telephone;

    public void setPassword(String password) {
        this.Password = PasswordHasher.hashPassword(password);
    }
    
    public boolean isPasswordValid(String password) {
        return PasswordHasher.verifyPassword(password, this.Password);
    }
}
