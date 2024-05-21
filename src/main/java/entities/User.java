package entities;

import com.services.PasswordHasher;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class User {
    @Id
    @GeneratedValue
    private int id;
    
    public User() {}
    
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
