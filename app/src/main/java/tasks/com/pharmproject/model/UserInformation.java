package tasks.com.pharmproject.model;

public class UserInformation {
   private String name, email, avatar, city, phone, uId;
   private boolean  isOnline;

    public UserInformation() {
    }

    public UserInformation(String name, String email, String avatar, String city, String phone, String uId) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.city = city;
        this.phone = phone;
        this.uId = uId;
    }

    public UserInformation(String name, String email, String avatar, String city, String uId) {
        this.name = name;
        this.email = email;
        this.avatar = avatar;
        this.city = city;
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }
}
