package in.codesquad.bepositive;

/**
 * Created by root on 12/2/17.
 */

public class User {
    String name;
    String email;
    String phone;
    String pincode;
    String password;
    String blood;

    public User(String name, String email, String phone, String pincode, String password, String blood) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pincode = pincode;
        this.password = password;
        this.blood = blood;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
