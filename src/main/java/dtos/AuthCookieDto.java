package dtos;

import java.time.LocalDateTime;
import java.util.Date;

public class AuthCookieDto {
    public LocalDateTime validUntil;
    public String username;
    public String name;
    public String telephone;
    
    public AuthCookieDto(LocalDateTime validUntil, String username, String name, String telephone) {
        this.validUntil = validUntil;
        this.username = username;
        this.name = name;
        this.telephone = telephone;
    }
    
    // Needed for Jackson deserialization
    public AuthCookieDto() {}
}
