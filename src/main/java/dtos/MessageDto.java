package dtos;


public class MessageDto {
    public String title;
    public String info;
    public boolean success;
    
    public MessageDto(String title, String message, boolean success) {
        this.title = title;
        this.info = message;
        this.success = success;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getInfo() {
        return info;
    }
    
    public void setInfo(String info) {
        this.info = info;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
