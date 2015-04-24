package com.plusplus.i.jongerenparticipatieplatfrom.model;

/**
 * Created by Shenno on 11/04/2015.
 */
public class Account {
    private String UserName;
    private String Email;
    private String Password;
    private String ConfirmPassword;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getConfirmPassword() {
        return ConfirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.ConfirmPassword = confirmPassword;
    }
}
